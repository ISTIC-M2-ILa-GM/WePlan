import { Component, OnInit } from '@angular/core';
import { AuthService } from './../../services/auth.service';
import { MzToastService } from 'ngx-materialize';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

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
    private toastService: MzToastService,
    private router: Router
  ) { }

  ngOnInit() {
    this.email = '';
    this.password = '';
    this.checkLogin();
  }

  checkLogin() {
    if (this.cookieService.check('JSESSIONID')) {
      this.authService.check().then(user => {
        this.router. navigateByUrl('/preference');
      }, error => {
        this.toastService.show('Something went wrong while trying to log you in :/', 4000, 'red');
      });
    }
  }
}
