import { Component, OnInit } from '@angular/core';
import { LoginRequest } from 'src/app/models/dto/login.request';
import { AuthService } from './../../services/auth.service';
import { MzToastService } from 'ngx-materialize';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;

  password: string;

  cookie: string;

  userId: string;

  constructor(
    private authService: AuthService,
    private cookieService: CookieService,
    private toastService: MzToastService
  ) { }

  ngOnInit() {
    this.email = '';
    this.password = '';
    this.checkLogin();
  }

  checkLogin() {
    if (this.cookieService.check('JSESSIONID')) {
      this.authService.check();
    }
  }

  onSubmit() {
    console.log(`email: ${this.email} pwd: ${this.password}`);
    const login: LoginRequest = {
      email: this.email,
      password: this.password
    };
  }

  submitCookie() {
    this.authService.setCookie(this.cookie);
    this.authService.login().then(isLoggedIn => {
      this.toastService.show('Successfully logged id !', 4000, 'green');
    });
  }
}
