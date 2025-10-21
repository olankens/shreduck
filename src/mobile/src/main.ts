import {bootstrapApplication} from '@angular/platform-browser';
import {RouteReuseStrategy, provideRouter, withPreloading, PreloadAllModules} from '@angular/router';
import {IonicRouteStrategy, provideIonicAngular} from '@ionic/angular/standalone';

import {routes} from './app/app.routes';
import {AppComponent} from './app/app.component';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {jwtInterceptor} from "./app/shared/jwt.interceptor";

bootstrapApplication(AppComponent, {
  providers: [
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
    provideIonicAngular(),
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideRouter(routes, withPreloading(PreloadAllModules)),
  ],
});
