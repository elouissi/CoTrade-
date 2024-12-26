import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthInterceptor } from './app/services/auth.interceptor';
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/app.component";
import {provideRouter} from "@angular/router";
import {routes} from "./app/app.routes";

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([AuthInterceptor])),
    provideRouter(routes)
  ]
});
