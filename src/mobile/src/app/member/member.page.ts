import {Component, EnvironmentInjector, inject} from '@angular/core';
import {IonIcon, IonLabel, IonTabBar, IonTabButton, IonTabs} from '@ionic/angular/standalone';
import {addIcons} from 'ionicons';
import {apps, construct, diamond} from 'ionicons/icons';

@Component({
  selector: 'app-member',
  imports: [IonTabs, IonTabBar, IonTabButton, IonIcon, IonLabel],
  template: `
    <ion-tabs>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="workouts" href="/member/workouts">
          <ion-icon aria-hidden="true" name="apps"></ion-icon>
          <ion-label>WORKOUTS</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="premium" href="/member/premium">
          <ion-icon aria-hidden="true" name="diamond"></ion-icon>
          <ion-label>PREMIUM</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="settings" href="/member/settings">
          <ion-icon aria-hidden="true" name="construct"></ion-icon>
          <ion-label>SETTINGS</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  `
})
export class MemberPage {
  public environmentInjector = inject(EnvironmentInjector);

  constructor() {
    addIcons({apps, construct, diamond});
  }
}
