import {Component, inject} from '@angular/core';
import {
  IonBadge,
  IonCol,
  IonContent,
  IonGrid,
  IonHeader,
  IonItem,
  IonList,
  IonRow,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {ActivatedRoute} from "@angular/router";
import {ExerciseControllerService} from "../openapi/services";
import {NgForOf, NgIf} from "@angular/common";
import {ExerciseDto} from "../openapi/models/exercise-dto";

@Component({
  selector: 'app-detail-exercise',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonItem,
    IonList,
    NgForOf,
    NgIf,
    IonBadge,
    IonGrid,
    IonRow,
    IonCol
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          {{ exercise?.name || 'Detail' }}
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">{{ exercise?.name || 'Detail' }}</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
      <video
        *ngIf="exercise"
        width="100%"
        autoplay
        muted
        loop
        preload="auto"
        [attr.src]="'http://localhost:8080/' + exercise?.media">
      </video>

      <ion-list inset="true" lines="none">
        <ion-grid>
          <ion-row>
            <ion-col size="6" *ngFor="let target of exercise?.exerciseTargets">
              <ion-badge style="display: block; height: 100%; width: 100%;">
                {{ target }}
              </ion-badge>
            </ion-col>
          </ion-row>
        </ion-grid>
      </ion-list>

      <ion-list inset="true" lines="none">
        <ion-item>
          {{ exercise?.description || '...' }}
        </ion-item>
      </ion-list>
    </ion-content>
  `
})
export class DetailExercisePage {
  private readonly exerciseService = inject(ExerciseControllerService);
  private readonly route = inject(ActivatedRoute);
  exercise: ExerciseDto | undefined;

  constructor() {
    const exerciseId = this.route.snapshot.paramMap.get('id');
    if (exerciseId) {
      this.exerciseService.detail1({id: parseInt(exerciseId, 10)}).subscribe({
        next: async (r) => {
          if (r instanceof Blob) {
            this.exercise = JSON.parse(await r.text());
          }
        },
        error: (e) => {
          console.error('Failed to fetch exercise', e);
        }
      });
    }
  }
}
