import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { NgIf } from '@angular/common';
import { UserRegistration, LoginCredentials } from '../../models/auth';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  constructor(private authService: AuthService, private router: Router){}

    registrationData = {
    username: '',
    email: '',
    password: '',
    password2: '',
  };

  errorMessage: String | null = null;

  matchPasswords(){
    if(this.registrationData.password === this.registrationData.password2)
    {
      return true
    }
    return false
  }

  onRegister() {
    console.log(this.registrationData);
    const registration: UserRegistration = {
      username: this.registrationData.username,
      email: this.registrationData.email,
      password:this.registrationData.password
    }
    this.authService.register(registration).subscribe({
      next: (response) => {
          console.log(response)
          const loginCreds: LoginCredentials = {
            username: this.registrationData.username,
            password: this.registrationData.password
          }

          this.authService.login(loginCreds)
      },
      error: (err) => {
        console.error('Login failed:', err);
        if (err.status === 401 || err.status === 403) {
          this.errorMessage = 'Registration failed. Username or Email may already be in use';
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again later.';
        }

      }

    })
  }
}
