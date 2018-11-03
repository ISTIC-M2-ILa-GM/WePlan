import {API} from './../config/api.config';
import {RestService} from './rest.service';
import {Injectable} from '@angular/core';
import {User} from './../models/user';
import {UserRequest} from '../models/dto/user.request';

const FAKE_USER = new User('Gautier', 'Rouleau', 'contact@gautier-rouleau.fr');

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private restService: RestService) { }

  get() {
    return this.restService.get(`${API.endpoint}${API.entities.users}`);
  }

  getOne(userId: number) {
    return this.restService.get(`${API.endpoint}${API.entities.users}/${userId}`);
  }

  update(userId: number, user: UserRequest) {
    return this.restService.put(`${API.endpoint}${API.entities.users}/${userId}`, user);
  }
}
