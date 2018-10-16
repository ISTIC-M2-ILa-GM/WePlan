import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { LoginRequest } from 'src/app/models/dto/login.request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;

  password: string;

  constructor(private userService: UserService) { }

  ngOnInit = () => {
    this.email = '';
    this.password = '';
  }

  onSubmit = () => {
    console.log(`email: ${this.email} pwd: ${this.password}`);
    const login: LoginRequest = {
      email: this.email,
      password: this.password
    };
    this.userService.login(login).subscribe(response => {
      console.log('Success !');
      console.log(response);
    }, e => {
      console.error(e);
    });
  }
}
