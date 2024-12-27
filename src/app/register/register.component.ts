import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  username = '';
  password = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    // this.error = '';
    // this.authService.register(this.username, this.password)
    //   .subscribe({
    //     next: () => {
    //       this.router.navigate(['/login']);
    //     },
    //     error: (err) => {
    //       this.error = 'Registration failed';
    //       console.error('Registration error:', err);
    //     }
    //   });
  }
}
