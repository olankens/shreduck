import {Component} from '@angular/core';
import {IonContent, IonHeader, IonTitle, IonToolbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-admin-premium',
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
          Premium
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Premium</ion-title>
        </ion-toolbar>
      </ion-header>
      <!-- Change content here -->
    </ion-content>
  `
})
export class PremiumPage {
  constructor() {
  }
}
