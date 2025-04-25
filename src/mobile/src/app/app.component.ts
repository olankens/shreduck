import {Component} from '@angular/core';
import {IonApp, IonRouterOutlet} from '@ionic/angular/standalone';
import {AdMob} from "@capacitor-community/admob";

@Component({
  selector: 'app-root',
  imports: [IonApp, IonRouterOutlet],
  template: `
    <ion-app>
      <ion-router-outlet></ion-router-outlet>
    </ion-app>
  `
})
export class AppComponent {
  constructor() {
    this.initAdMob();
  }

  private async initAdMob() {
    try {
      await AdMob.initialize();
      console.log('✅ AdMob initialized');
    } catch (error) {
      console.error('❌ Failed to initialize AdMob', error);
    }
  }
}
