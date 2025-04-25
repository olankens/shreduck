import {Component, inject, signal} from '@angular/core';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {addIcons} from "ionicons";
import {add, create, ellipsisVertical, trash} from "ionicons/icons";
import {Router} from "@angular/router";
import {PresetControllerService} from "../../openapi/services/preset-controller.service";
import {NgForOf, NgIf} from "@angular/common";
import {ViewWillEnter} from '@ionic/angular';

@Component({
  selector: 'app-member-workouts',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButtons,
    IonButton,
    IonIcon,
    IonList,
    IonItem,
    IonLabel,
    NgIf,
    NgForOf
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Workouts
        </ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="onCreateWorkout()">
            <ion-icon slot="icon-only" name="add"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Workouts</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
      <ion-list inset="true" lines="none" *ngIf="workouts().length > 0">
        <ion-item
          button="true"
          *ngFor="let workout of workouts(); let last = last"
          [class.no-line]="last">
          <ion-label
            (click)="onLaunchWorkout(workout)"
          >{{ workout.name }}
          </ion-label>
          <ion-button slot="end" size="small" color="danger" (click)="onDeleteWorkout(workout)">
            <ion-icon slot="icon-only" name="trash"></ion-icon>
          </ion-button>
        </ion-item>
      </ion-list>
      <div *ngIf="workouts().length === 0" class="empty-state">
        <h1>No workouts yet</h1>
        <small>Tap + to create one!</small>
      </div>
    </ion-content>
  `
})
export class WorkoutsPage implements ViewWillEnter {
  private readonly presetService = inject(PresetControllerService);
  private readonly router = inject(Router);
  workouts = signal<any[]>([]);

  constructor() {
    addIcons({add, create, ellipsisVertical, trash});
  }

  ionViewWillEnter() {
    this.presetService.export().subscribe({
      next: async (r) => {
        if (r instanceof Blob) {
          const parsed = JSON.parse(await r.text());
          this.workouts.set(parsed);
        }
      },
      error: (e) => {
        console.error('Failed to fetch workouts', e);
      }
    });
  }

  onCreateWorkout() {
    this.router.navigateByUrl("create-workout", {replaceUrl: true}).then();
  }

  onDeleteWorkout(workout: any) {
    this.presetService.delete({id: workout.id}).subscribe({
      next: () => {
        const updated = this.workouts().filter(w => w.id !== workout.id);
        this.workouts.set(updated);
      },
      error: (e) => {
        console.error('Failed to delete workout', e);
      }
    });
  }

  onLaunchWorkout(workout: any) {
    this.router.navigate(['/launch-workout', workout.id]).then();
  }
}
