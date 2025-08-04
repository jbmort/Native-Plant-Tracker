import { Component } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { NgIf } from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

    registrationData = {
    username: '',
    email: '',
    password: '',
    password2: '',
  };

  matchPasswords(){
    if(this.registrationData.password === this.registrationData.password2)
    {
      return true
    }
    return false
  }

  onRegister() {
    console.log(this.registrationData);
  }
}
