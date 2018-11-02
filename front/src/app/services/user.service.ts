import { API } from './../config/api.config';
import { RestService } from './rest.service';
import { Injectable } from '@angular/core';
import { User } from '../models/dto/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private restService: RestService) { }

  get() {
    return this.restService.get(`${API.endpoint}${API.entities.user}/0`);
  }

  getCurrentUser() {
    //TODO where is current user ?
    return this.restService.get(`${API.endpoint}${API.entities.user}/0`);
  }

  post(user: User) {
    return this.restService.post(`${API.endpoint}${API.entities.user}`, user);
  }

  addEventToCurrentUser(id: number) {
    //TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.events}`, [id]);
  }

  removeEventToCurrentUser(id: number) {
    //TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.events}`, [id]);
  }
}
