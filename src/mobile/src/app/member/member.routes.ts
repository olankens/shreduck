import { Routes } from '@angular/router';
import { MemberPage } from './member.page';

export const routes: Routes = [
  {
    path: 'member',
    component: MemberPage,
    children: [
      {
        path: 'workouts',
        loadComponent: () =>
          import('./pages/workouts.page').then((m) => m.WorkoutsPage),
      },
      {
        path: 'premium',
        loadComponent: () =>
          import('./pages/premium.page').then((m) => m.PremiumPage),
      },
      {
        path: 'settings',
        loadComponent: () =>
          import('./pages/settings.page').then((m) => m.SettingsPage),
      },
      {
        path: '',
        redirectTo: '/member/workouts',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '',
    redirectTo: '/member/workouts',
    pathMatch: 'full',
  },
];
