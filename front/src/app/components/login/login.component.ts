import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;

  password: string;

  constructor() { }

  ngOnInit = () => {
    this.email = '';
    this.password = '';
  }

  onSubmit = () => {
    console.log(`email: ${this.email} pwd: ${this.password}`);
  }
}
