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
import {AuthControllerService} from "../openapi/services";
import {AlertController} from "@ionic/angular";

@Component({
  selector: 'app-login',
  imports: [
    IonHeader,
    IonTitle,
    IonToolbar,
    IonContent,
    IonButton,
    IonButtons,
    IonIcon,
    ReactiveFormsModule,
    IonInput,
    IonList,
    IonInputPasswordToggle
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Login
        </ion-title>
        <ion-buttons slot="end">
          <ion-button
            (click)="onForwardClick()"
            [disabled]="form.invalid"
          >
            <ion-icon slot="icon-only" name="arrow-forward"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Login</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-list inset="true" lines="full" class="ion-padding">
        <form [formGroup]="form" (ngSubmit)="onForwardClick()">
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
export class LoginPage {
  private readonly alertController = inject(AlertController);
  private readonly authService = inject(AuthControllerService);
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);

  form: FormGroup = this.fb.group({
    pseudo: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor() {
    addIcons({arrowForward});
  }

  async onForwardClick() {
    if (this.form.invalid) return;
    this.form.markAsTouched();
    this.authService.login({body: {form: this.form.value}}).subscribe({
      next: (r: any) => {
        r.text().then((text: string) => {
          const parsed = JSON.parse(text);
          alert(parsed);
          const memberRole = parsed.member.memberRole;
          localStorage.setItem('jwt', parsed.token);
          this.router.navigateByUrl(
            (memberRole === "ADMIN") ? '/admin' : '/member',
            {replaceUrl: true}
          ).then();
        });
      },
      error: async e => {
        console.error('Login error', e);
        const errorAlert = await this.alertController.create({
          header: 'Login Failed',
          message: e.message,
          buttons: ['OK']
        });
        await errorAlert.present();
      }
    });
  }
}
