// login.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from "@angular/forms";
import { Router } from '@angular/router';
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule
  ],
  templateUrl: 'login.component.html'
})
export class LoginComponent {
  username: string = '';  // Initialize with empty string
  password: string = '';  // Initialize with empty string

  constructor(
      private authService: AuthService,
      private router: Router
  ) {}

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        localStorage.setItem('access_token', response.access_token);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Login failed', err);
      }
    });
  }
}
