import {Component, EnvironmentInjector, inject} from '@angular/core';
import {IonIcon, IonLabel, IonTabBar, IonTabButton, IonTabs} from '@ionic/angular/standalone';
import {addIcons} from 'ionicons';
import {barbell, construct, diamond} from 'ionicons/icons';

@Component({
  selector: 'app-admin',
  imports: [IonTabs, IonTabBar, IonTabButton, IonIcon, IonLabel],
  template: `
    <ion-tabs>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="exercises" href="/admin/exercises">
          <ion-icon aria-hidden="true" name="barbell"></ion-icon>
          <ion-label>EXERCISES</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="premium" href="/admin/premium">
          <ion-icon aria-hidden="true" name="diamond"></ion-icon>
          <ion-label>PREMIUM</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="settings" href="/admin/settings">
          <ion-icon aria-hidden="true" name="construct"></ion-icon>
          <ion-label>SETTINGS</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  `
})
export class AdminPage {
  public environmentInjector = inject(EnvironmentInjector);

  constructor() {
    addIcons({barbell, construct, diamond});
  }
}
