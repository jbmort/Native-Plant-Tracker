import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ RouterLink ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
authService: AuthService;
  router: Router = new Router;

  constructor(
    router: Router,
    authService: AuthService,
  ){
    this.authService = authService;
  }
  ngOnInit(): void {
    if(this.authService.isAuthenticated()){
      this.router.navigate(['dashboard'])
    }
  }

}
