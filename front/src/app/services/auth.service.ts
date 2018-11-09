import { User } from './../models/dto/user';
import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { UserRequest } from '../models/dto/user.request';
import { Observable } from 'rxjs';
import { API } from '../config/api.config';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private restService: RestService,
    private cookieService: CookieService,
    private router: Router
  ) { }

  check(): Promise<Object> {
    return new Promise<Object>((resolve, reject) => {
      this.restService.get(`${API.endpoint}${API.auth}${API.current}`).subscribe((user: Object) => {
        this.setUser(user);
        resolve(user);
      }, error => {
        console.error("y'a eu une bourde");
        console.error(error);
        this.cookieService.deleteAll();
        this.router.navigateByUrl('/login');
        reject();
      });
    });
  }

  setCookie(sessionId: string) {
    this.cookieService.set('JSESSIONID', sessionId);
  }

  setUser(user: Object) {
    this.cookieService.set('user', JSON.stringify(user));
  }

  getUser(): User {
    return JSON.parse(this.cookieService.get('user'));
  }

  register(user: UserRequest): Observable<ArrayBuffer> {
    return this.restService.post(`${API.endpoint}${API.entities.user}`, user);
  }

  /*
  login(login: LoginRequest) {
    let body = new URLSearchParams();
    body.set('email', login.email);
    body.set('password', login.password);

    const options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.restService.post(`/backend/login`, body.toString(), options);
  }
  */
}
