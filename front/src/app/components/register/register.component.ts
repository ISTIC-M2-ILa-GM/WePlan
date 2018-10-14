import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  repeat = '';

  constructor() { }

  ngOnInit = () => {
  }

  onSubmit = () => {
    console.log(`First name: ${this.firstName} Last name: ${this.lastName}`);
    console.log(`Email: ${this.email}`);
    console.log(`Password: ${this.password} Repeat: ${this.repeat}`)
  }
}
