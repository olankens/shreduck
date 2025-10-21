import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./extra/login.page').then((m) => m.LoginPage),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./extra/register.page').then((m) => m.RegisterPage),
  },
  {
    path: 'detail-exercise/:id',
    loadComponent: () =>
      import('./workout/detail-exercise.page').then((m) => m.DetailExercisePage),
  },
  {
    path: 'create-workout',
    loadComponent: () =>
      import('./workout/create-workout.page').then((m) => m.CreateWorkoutPage),
  },
  {
    path: 'launch-workout/:id',
    loadComponent: () =>
      import('./workout/launch-workout.page').then((m) => m.LaunchWorkoutPage),
  },
  {
    path: 'select-exercises',
    loadComponent: () =>
      import('./workout/select-exercises.page').then((m) => m.SelectExercisesPage),
  },
  {
    path: '',
    loadChildren: () =>
      import('./admin/admin.routes').then((m) => m.routes),
  },
  {
    path: '',
    loadChildren: () =>
      import('./member/member.routes').then((m) => m.routes),
  },
];
