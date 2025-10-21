import {Routes} from '@angular/router';
import {AdminPage} from "./admin.page";

export const routes: Routes = [
  {
    path: 'admin',
    component: AdminPage,
    children: [
      {
        path: 'exercises',
        loadComponent: () =>
          import('./pages/exercises.page').then((m) => m.ExercisesPage),
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
        redirectTo: '/admin/exercises',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '',
    redirectTo: '/admin/exercises',
    pathMatch: 'full',
  },
];
