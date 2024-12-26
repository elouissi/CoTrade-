// app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import {RegisterComponent} from "./register/register.component";


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {path: 'register', component: RegisterComponent }
];
