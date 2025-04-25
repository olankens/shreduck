import {Component} from '@angular/core';
import {IonContent, IonHeader, IonTitle, IonToolbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-admin-settings',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>
          Settings
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Settings</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
    </ion-content>
  `
})
export class SettingsPage {
  constructor() {
  }
}
