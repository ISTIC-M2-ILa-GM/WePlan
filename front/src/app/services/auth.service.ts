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

  check(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.restService.get(`${API.endpoint}${API.entities.users}/current`).subscribe((user: Object) => {
        console.log(user);
        resolve(true);
      }, error => {
        this.cookieService.deleteAll();
        this.router.navigateByUrl('/login');
        resolve(false);
      });
    });
  }

  setCookie(sessionId: string) {
    this.cookieService.set('JSESSIONID', sessionId);
  }

  setUser(user: Object) {
    this.cookieService.set('user', JSON.stringify(user));
  }

  getUser(): Object {
    return JSON.parse(this.cookieService.get('user'));
  }

  register(user: UserRequest): Observable<ArrayBuffer> {
    return this.restService.post(`${API.endpoint}${API.entities.users}`, user);
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
