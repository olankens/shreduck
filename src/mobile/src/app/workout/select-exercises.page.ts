import {Component, inject} from '@angular/core';
import {
  IonButton,
  IonContent,
  IonFooter,
  IonHeader,
  IonItem,
  IonLabel,
  IonList,
  IonTitle,
  IonToggle,
  IonToolbar,
  NavController,
} from "@ionic/angular/standalone";
import {ExerciseDto} from "../openapi/models/exercise-dto";
import {ExerciseControllerService} from "../openapi/services";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-select-exercises',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButton,
    IonFooter,
    IonList,
    IonItem,
    IonLabel,
    NgIf,
    NgForOf,
    IonToggle
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Select Exercises
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Create New Workout</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
      <ion-list inset="true" lines="none" *ngIf="exercises.length > 0">
        <ion-item
          *ngFor="let exercise of exercises; let last = last"
          [class.no-line]="last">
          <ion-label
            (click)="onDetailExercise(exercise)"
          >
            {{ exercise.name }}
          </ion-label>
          <ion-toggle
            slot="end"
            [checked]="isSelected(exercise)"
            (ionChange)="toggleSelection(exercise)">
          </ion-toggle>
        </ion-item>
      </ion-list>
      <ion-item *ngIf="exercises.length === 0">
        <ion-label>No exercises available.</ion-label>
      </ion-item>
    </ion-content>

    <ion-footer>
      <ion-toolbar>
        <div class="ion-padding-horizontal ion-padding-vertical">
          <ion-button
            expand="block"
            (click)="addSelected()">
            Add Selected ({{ selected.length }})
          </ion-button>
        </div>
      </ion-toolbar>
    </ion-footer>
  `
})
export class SelectExercisesPage {
  private exerciseService = inject(ExerciseControllerService);
  private navController = inject(NavController);
  private readonly router = inject(Router);
  exercises: ExerciseDto[] = [];
  selected: ExerciseDto[] = [];

  constructor() {
    this.exerciseService.export1().subscribe({
      next: async (r) => {
        if (r instanceof Blob) {
          this.exercises = JSON.parse(await r.text());
        }
      },
      error: (e) => {
        console.error('Failed to fetch exercises', e);
      }
    });
  }

  isSelected(exercise: ExerciseDto): boolean {
    return this.selected.some(e => e.id === exercise.id);
  }

  toggleSelection(exercise: ExerciseDto) {
    if (this.isSelected(exercise)) {
      this.selected = this.selected.filter(e => e.id !== exercise.id);
    } else {
      this.selected.push(exercise);
    }
  }

  addSelected() {
    this.navController.navigateBack('create-workout', {
      replaceUrl: true,
      state: {selectedExercises: this.selected}
    }).then();
  }

  onDetailExercise(exercise: any) {
    this.router.navigate(['/detail-exercise', exercise.id]).then();
  }
}
