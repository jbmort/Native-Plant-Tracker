import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink } from '@angular/router';
import { AuthService } from './auth.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'NativePlantTrackerFrontEnd';
 constructor(public authService: AuthService, private router: Router) {}

  logout(): void {
    // Remove the token from storage
    localStorage.removeItem('accessToken');
    // Redirect to the home or login page
    this.router.navigate(['/']);
    console.log('User logged out.');
  }
}
