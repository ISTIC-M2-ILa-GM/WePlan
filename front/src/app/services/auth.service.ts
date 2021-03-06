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
    return (this.cookieService.check('user') ? JSON.parse(this.cookieService.get('user')) : null);
  }

  register(user: UserRequest): Observable<ArrayBuffer> {
    return this.restService.post(`${API.endpoint}${API.entities.user}`, user);
  }
}
