import {Component, inject, OnInit, signal} from '@angular/core';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonFooter,
  IonHeader,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonReorder,
  IonReorderGroup,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {addIcons} from "ionicons";
import {checkmark} from "ionicons/icons";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AlertController, ItemReorderEventDetail} from "@ionic/angular";
import {NavigationEnd, Router} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {PresetControllerService} from "../openapi/services/preset-controller.service";
import {PresetDto} from "../openapi/models/preset-dto";

@Component({
  selector: 'app-create-workout',
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
    IonFooter,
    IonReorderGroup,
    IonReorder,
    NgForOf,
    NgIf
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Create New Workout
        </ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="createWorkout()">
            <ion-icon slot="icon-only" name="checkmark"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Create New Workout</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
      <ion-list inset="true" lines="none">
        <ion-item button (click)="changeField('name')">
          <ion-label>Name</ion-label>
          <ion-label slot="end">{{ form.get('name')?.value }}</ion-label>
        </ion-item>
      </ion-list>

      <ion-list inset="true" lines="none" *ngIf="selectedExercises().length > 0">
        <ion-reorder-group
          (ionItemReorder)="reorderItems($event)"
          [disabled]="false">
          <ion-item *ngFor="let exercise of selectedExercises()">
            <ion-label>{{ exercise.name }}</ion-label>
            <ion-reorder slot="end"></ion-reorder>
          </ion-item>
        </ion-reorder-group>
      </ion-list>
    </ion-content>

    <ion-footer>
      <ion-toolbar>
        <div class="ion-padding-horizontal ion-padding-vertical">
          <ion-button expand="block" (click)="onAppendExercises()">Add Exercises</ion-button>
        </div>
      </ion-toolbar>
    </ion-footer>
  `
})
export class CreateWorkoutPage implements OnInit {
  private readonly alertController = inject(AlertController);
  private readonly fb = inject(FormBuilder);
  private readonly presetService = inject(PresetControllerService);
  private readonly router = inject(Router);
  selectedExercises = signal<any[]>([]);

  constructor() {
    addIcons({checkmark});
    this.router.events.subscribe((e) => {
      if (e instanceof NavigationEnd) {
        this.restoreSelectedExercises();
      }
    });
  }

  ngOnInit() {
    this.restoreSelectedExercises();
  }

  form: FormGroup = this.fb.group({
    name: ['', [Validators.required]],
  });

  async changeField(field: string) {
    const control = this.form.get(field);
    if (control) {
      document.activeElement && (document.activeElement as HTMLElement).blur();
      const alert = await this.alertController.create({
        header: `Enter ${field}`,
        inputs: [
          {
            name: 'value',
            type: 'text',
            value: control.value,
            placeholder: `Enter new ${field}`,
          }
        ],
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel'
          },
          {
            text: 'OK',
            handler: (data) => control.setValue(data.value)
          }
        ]
      });
      await alert.present();
    }
  }

  createWorkout() {
    if (this.form.invalid || this.selectedExercises().length === 0) return;
    const name = this.form.get('name')?.value;
    const presetExercises = this.selectedExercises()
      .map((exercise, index) => ({exerciseId: exercise.id, position: index}));
    this.presetService.create({body: {form: {name, presetExercises, presetType: 'FREE'}}}).subscribe({
      next: (r: PresetDto) => {
        sessionStorage.removeItem('selectedExercises');
        this.router.navigateByUrl('/member/workouts', {replaceUrl: true}).then();
      },
      error: err => {
        console.error('Failed to create preset', err);
      }
    });
  }

  onAppendExercises() {
    this.router.navigateByUrl('/select-exercises').then();
  }

  reorderItems(event: CustomEvent<ItemReorderEventDetail>) {
    const movedItem = this.selectedExercises()[event.detail.from];
    this.selectedExercises()[event.detail.from] = this.selectedExercises()[event.detail.to];
    this.selectedExercises()[event.detail.to] = movedItem;
    this.selectedExercises.set([...this.selectedExercises()]);
    event.detail.complete();
    sessionStorage.setItem('selectedExercises', JSON.stringify(this.selectedExercises()));
  }

  restoreSelectedExercises() {
    const storedExercises = sessionStorage.getItem('selectedExercises');
    if (storedExercises) this.selectedExercises.set(JSON.parse(storedExercises));
    if (history.state && history.state.selectedExercises) {
      this.selectedExercises.set([...this.selectedExercises(), ...history.state.selectedExercises]);
      sessionStorage.setItem('selectedExercises', JSON.stringify(this.selectedExercises()));
    }
    sessionStorage.removeItem('selectedExercises');
  }
}
