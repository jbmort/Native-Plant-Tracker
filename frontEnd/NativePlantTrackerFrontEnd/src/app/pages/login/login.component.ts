import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(  ){}

   loginData = {
    username: '',
    password: ''
  };

  onSubmit() {
    // Handle login logic here
    console.log(this.loginData);
  }

}
