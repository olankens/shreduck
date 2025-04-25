import {Component, inject} from '@angular/core';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonIcon,
  IonInput,
  IonInputPasswordToggle,
  IonList,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {addIcons} from "ionicons";
import {arrowForward} from "ionicons/icons";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  imports: [
    IonHeader,
    IonTitle,
    IonToolbar,
    IonContent,
    IonButton,
    IonButtons,
    IonIcon,
    IonInput,
    IonInputPasswordToggle,
    IonList,
    ReactiveFormsModule
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Register
        </ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="onForwardClick()">
            <ion-icon slot="icon-only" name="arrow-forward"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Register</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-list inset="true" lines="full" class="ion-padding">
        <form [formGroup]="form" (ngSubmit)="onForwardClick()">
          <ion-input
            label="Email"
            fill="outline"
            labelPlacement="stacked"
            formControlName="email"
            type="email"
            placeholder="Your email here"/>
          <br>
          <ion-input
            label="Pseudo"
            fill="outline"
            labelPlacement="stacked"
            formControlName="pseudo"
            placeholder="Your pseudo here"/>
          <br>
          <ion-input
            label="Password"
            fill="outline"
            labelPlacement="stacked"
            formControlName="password"
            type="password"
            placeholder="Your password here">
            <ion-input-password-toggle slot="end"></ion-input-password-toggle>
          </ion-input>
        </form>
      </ion-list>
    </ion-content>
  `
})
export class RegisterPage {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);

  form: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    pseudo: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor() {
    addIcons({arrowForward});
  }

  onForwardClick() {
    this.router.navigateByUrl('/member', {replaceUrl: true}).then();
  }
}
