import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginCredentials } from '../../models/auth';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private authService: AuthService, private router: Router
   ){}

   loginData = {
    username: '',
    password: ''
  };

  errorMessage: string | null = null;


  onSubmit() {

    console.log(this.loginData);

    this.errorMessage = null; 

    const creds: LoginCredentials = {
      username: this.loginData.username,
      password: this.loginData.password,
    }

    this.authService.login(creds).subscribe({

      next: (response) => {
              console.log('Login successful!', response);

            },
      error: (err) => {
        console.error('Login failed:', err);
        if (err.status === 401 || err.status === 403) {
          this.errorMessage = 'Login failed. Please check your username and password.';
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again later.';
        }
      }
    });    
  }

}
