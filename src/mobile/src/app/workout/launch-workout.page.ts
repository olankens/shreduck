import {Component, inject} from '@angular/core';
import {
  IonButton,
  IonButtons,
  IonCol,
  IonContent,
  IonFooter,
  IonGrid,
  IonHeader,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonRow,
  IonSpinner,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {ActivatedRoute, Router} from "@angular/router";
import {PresetControllerService} from "../openapi/services";
import {NgForOf, NgIf} from "@angular/common";
import {PresetExerciseDto} from "../openapi/models/preset-exercise-dto";
import {addIcons} from "ionicons";
import {arrowBack, close} from "ionicons/icons";

@Component({
  selector: 'app-launch-workout',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    NgIf,
    NgForOf,
    IonItem,
    IonLabel,
    IonList,
    IonButton,
    IonFooter,
    IonSpinner,
    IonButtons,
    IonGrid,
    IonRow,
    IonCol,
    IonIcon
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          {{ workout?.name || 'Exercises' }}
        </ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="onCancelWorkout()" color="danger">
            <ion-icon slot="icon-only" name="close"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">{{ workout?.name || 'Exercises' }}</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
      <video
        width="100%"
        autoplay
        muted
        loop
        playsinline
        [attr.src]="'http://localhost:8080/' + currentExercise?.exercise?.media"
      >
        Your browser does not support the video tag.
      </video>

      <ion-list inset="true" lines="none" *ngIf="workout">
        <ion-item
          *ngFor="let exercise of workout.presetExercises; let i = index; let last = last"
          [class.no-line]="last">
          <ion-label>{{ exercise.exercise.name }}</ion-label>
          <ion-spinner
            *ngIf="i === currentIndex"
            name="lines-sharp-small"
            slot="end"
            color="primary"
          ></ion-spinner>
        </ion-item>
      </ion-list>
    </ion-content>

    <ion-footer *ngIf="currentExercise">
      <ion-toolbar>
        <ion-grid>
          <ion-row>
            <ion-col size="4">
              <ion-button
                color="medium"
                (click)="onLaunchPrevious()"
                [disabled]="currentIndex === 0"
                expand="block"
              >
                Prev
              </ion-button>
            </ion-col>
            <ion-col
              size="8"
              *ngIf="!isLastExercise()">
              <ion-button
                expand="block"
                (click)="onLaunchNext()"
              >
                Next
              </ion-button>
            </ion-col>
            <ion-col
              size="8"
              *ngIf="isLastExercise()">
              <ion-button
                color="success"
                expand="block"
                (click)="onFinishWorkout()"
              >
                Done
              </ion-button>
            </ion-col>
          </ion-row>
        </ion-grid>
      </ion-toolbar>
    </ion-footer>
  `
})
export class LaunchWorkoutPage {
  private readonly presetService = inject(PresetControllerService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  currentExercise: PresetExerciseDto | undefined;
  currentIndex = 0;
  workout: any;

  constructor() {
    addIcons({arrowBack, close});
    const workoutId = this.route.snapshot.paramMap.get('id');
    if (workoutId) {
      this.presetService.detail({id: parseInt(workoutId, 10)}).subscribe({
        next: async (r) => {
          if (r instanceof Blob) {
            this.workout = JSON.parse(await r.text());
            this.currentExercise = this.workout.presetExercises[0];
            this.currentIndex = 0;
          }
        },
        error: (e) => {
          console.error('Failed to fetch workout', e);
        }
      });
    }
  }

  isLastExercise(): boolean {
    return this.workout && this.currentIndex === this.workout.presetExercises.length - 1;
  }

  onCancelWorkout() {
    this.router.navigateByUrl("/member/workouts", {replaceUrl: true}).then();
  }

  onFinishWorkout() {
    this.router.navigateByUrl("/member/workouts", {replaceUrl: true}).then();
  }

  onLaunchNext() {
    if (this.workout && this.currentIndex < this.workout.presetExercises.length - 1) {
      this.currentIndex++;
      this.currentExercise = this.workout.presetExercises[this.currentIndex];
    }
  }

  onLaunchPrevious() {
    if (this.workout && this.currentIndex > 0) {
      this.currentIndex--;
      this.currentExercise = this.workout.presetExercises[this.currentIndex];
    }
  }
}
