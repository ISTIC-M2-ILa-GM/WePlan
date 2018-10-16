import { API } from './../config/api.config';
import { RestService } from './rest.service';
import { Injectable } from '@angular/core';
import { User } from './../models/user';
import { UserRequest } from '../models/dto/user.request';
import { LoginRequest } from '../models/dto/login.request';
import { HttpHeaders } from '@angular/common/http';

const FAKE_USER = new User('Gautier', 'Rouleau', 'contact@gautier-rouleau.fr');

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private restService: RestService) { }

  get() {
    return this.restService.get(`${API.endpoint}${API.entities.users}/0`);
  }

  post(user: UserRequest) {
    return this.restService.post(`${API.endpoint}${API.entities.users}`, user);
  }

  login(login: LoginRequest) {
    const body = new URLSearchParams();
    body.set('email', login.email);
    body.set('password', login.password);

    const options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.restService.post(`${API.endpoint}${API.login}`, body.toString(), options);
  }
}
