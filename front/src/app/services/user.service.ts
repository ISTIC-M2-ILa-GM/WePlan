import { API } from './../config/api.config';
import { RestService } from './rest.service';
import { Injectable } from '@angular/core';
import { User } from './../models/user';

const FAKE_USER = new User('Gautier', 'Rouleau', 'contact@gautier-rouleau.fr');

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private restService: RestService) { }

  get() {
    return this.restService.get(`${API.endpoint}${API.entities.users}/0`);
  }

  post(user: User) {
    return this.restService.post(`${API.endpoint}${API.entities.users}`, user);
  }
}
