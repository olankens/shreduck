import {Component, inject, signal} from '@angular/core';
import {
  IonButton,
  IonContent,
  IonHeader,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {NgForOf, NgIf} from "@angular/common";
import {AlertController, ToastController, ViewWillEnter} from "@ionic/angular";
import {PresetControllerService} from "../../openapi/services/preset-controller.service";
import {Router} from "@angular/router";
import {addIcons} from "ionicons";
import {lockClosed, trash} from "ionicons/icons";
import {UnlockablePresetDto} from "../../openapi/models/unlockable-preset-dto";
import {Capacitor} from "@capacitor/core";
import {AdMob, RewardAdPluginEvents} from "@capacitor-community/admob";

@Component({
  selector: 'app-member-premium',
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButton,
    IonIcon,
    IonItem,
    IonLabel,
    IonList,
    NgForOf,
    NgIf
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar>
        <ion-title>Premium</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content [fullscreen]="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Premium</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-list inset="true" lines="none" *ngIf="premiums().length > 0">
        <ion-item
          *ngFor="let premium of premiums(); let last = last"
          [class.no-line]="last">
          <ion-label><h2>{{ premium.preset?.name }}</h2></ion-label>
          <ion-button
            *ngIf="!premium.unlocked"
            slot="end"
            size="small"
            color="primary"
            (click)="unlockPreset(premium.preset?.id)">
            <ion-icon slot="icon-only" name="lock-closed"></ion-icon>
          </ion-button>
        </ion-item>
      </ion-list>
    </ion-content>
  `
})
export class PremiumPage implements ViewWillEnter {
  private readonly alertController = inject(AlertController);
  private readonly presetService = inject(PresetControllerService);
  private readonly router = inject(Router);
  private readonly toastController = inject(ToastController);
  premiums = signal<UnlockablePresetDto[]>([]);

  constructor() {
    addIcons({lockClosed, trash});
  }

  ionViewWillEnter() {
    this.presetService.exportUnlockable().subscribe({
      next: async (r) => {
        if (r instanceof Blob) {
          const parsed = JSON.parse(await r.text());
          this.premiums.set(parsed);
        }
      },
      error: (e) => {
        console.error('Failed to fetch workouts', e);
      }
    });
  }

  async unlockPreset(id?: number) {
    if (!id) return;
    const alert = await this.alertController.create({
      header: 'Unlocking Preset',
      message: 'You will have to watch a video advert to unlock',
      buttons: [
        {
          text: 'No',
          role: 'cancel'
        },
        {
          text: 'Yes',
          handler: () => this.showRewardedAd(id)
        }
      ]
    });
    await alert.present();
  }

  async showRewardedAd(id: number) {
    if (Capacitor.getPlatform() === 'web') {
      await this.presetService.unlock({id}).toPromise();
      await this.toastController.create({
        message: 'Unlocked (simulated in browser)',
        duration: 2000,
        color: 'success'
      }).then(t => t.present());
      this.ionViewWillEnter();
      return;
    }

    const rewardListener = await AdMob.addListener(
      RewardAdPluginEvents.Rewarded,
      async () => {
        await this.presetService.unlock({id}).toPromise();
        await this.toastController.create({
          message: 'Unlocked successfully!',
          duration: 2000,
          color: 'success'
        }).then(t => t.present());
        this.ionViewWillEnter();
        await rewardListener.remove();
      }
    );
    try {
      await AdMob.prepareRewardVideoAd({
        adId: 'ca-app-pub-3940256099942544/5224354917',
        isTesting: true
      });
      await AdMob.showRewardVideoAd();
    } catch (error) {
      console.error('Ad failed to load or show', error);
      await this.toastController.create({
        message: 'Ad failed to load. Try again later.',
        duration: 2000,
        color: 'danger'
      }).then(t => t.present());
      await rewardListener.remove();
    }
  }
}
