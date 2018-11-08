import { Component, OnInit } from '@angular/core';
import { LoginRequest } from 'src/app/models/dto/login.request';
import { AuthService } from './../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;

  password: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.email = '';
    this.password = '';
    this.authService.check();
  }

  onSubmit = () => {
    console.log(`email: ${this.email} pwd: ${this.password}`);
    const login: LoginRequest = {
      email: this.email,
      password: this.password
    };
    this.authService.login(login).subscribe(response => {
      console.log('Success !');
      console.log(response);
    }, e => {
      console.error(e);
    });
  }
}
