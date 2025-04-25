import {Component, inject} from '@angular/core';
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
import {checkmark, close} from "ionicons/icons";
import {AlertController, LoadingController, ToastController} from "@ionic/angular";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-member-settings',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButton,
    IonButtons,
    IonIcon,
    IonList,
    IonItem,
    IonLabel
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Settings
        </ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="updateProfile()">
            <ion-icon slot="icon-only" name="checkmark"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Settings</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-list inset="true" lines="none">
        <ion-item button (click)="changeField('email')">
          <ion-label>Email</ion-label>
          <ion-label slot="end">{{ form.get('email')?.value }}</ion-label>
        </ion-item>
        <ion-item button (click)="changeField('password')">
          <ion-label>Password</ion-label>
          <ion-label slot="end">••••••••••</ion-label>
        </ion-item>
        <ion-item button (click)="changeField('pseudo')">
          <ion-label>Pseudo</ion-label>
          <ion-label slot="end">{{ form.get('pseudo')?.value }}</ion-label>
        </ion-item>
      </ion-list>
    </ion-content>
  `
})
export class SettingsPage {
  private readonly alertController = inject(AlertController);
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly toastController = inject(ToastController);
  private readonly loadingController = inject(LoadingController);

  form: FormGroup = this.fb.group({
    email: ['member@member.com', [Validators.required, Validators.email]],
    pseudo: ['member', Validators.required],
    password: ['member', Validators.required],
  });

  constructor() {
    addIcons({checkmark, close});
  }

  async changeField(field: 'email' | 'password' | 'pseudo') {
    const control = this.form.get(field);
    if (control) {
      document.activeElement && (document.activeElement as HTMLElement).blur();
      const label = field.charAt(0).toUpperCase() + field.slice(1);
      const alert = await this.alertController.create({
        header: `Update ${label}`,
        inputs: [
          {
            name: 'value',
            type: field === 'password' ? 'password' : 'text',
            value: control.value,
            placeholder: `Enter new ${field}`
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

  async outputLoading() {
    document.activeElement && (document.activeElement as HTMLElement).blur();
    const loading = await this.loadingController.create({
      message: 'Updating...',
    });
    await loading.present();
    return loading;
  }

  async outputToast(message: string, color: string = 'success') {
    const toast = await this.toastController.create({
      animated: true,
      color: color,
      duration: 2000,
      message: message,
      position: 'bottom',
      buttons: [
        {
          icon: 'close',
          role: 'cancel',
          side: 'end',
        }
      ]
    });
    await toast.present();
  }

  async updateProfile() {
    if (this.form.valid) {
      const updated = this.form.value;
      console.log('Updated profile:', updated);
      const loading = await this.outputLoading();
      try {
        // TODO: Make API call to update member information
        await (new Promise(_ => setTimeout(_, 2000)));
        await loading.dismiss();
        await this.outputToast('Profile was updated with success!');
        await this.router.navigateByUrl('/member/workouts');
      } catch (error) {
        await loading.dismiss();
        await this.outputToast('Profile was not updated! Please try again.', 'danger');
      }
    } else {
      // TODO: Output form errors properly
      await this.outputToast('Please fill out all fields correctly.', 'warning');
    }
  }
}
