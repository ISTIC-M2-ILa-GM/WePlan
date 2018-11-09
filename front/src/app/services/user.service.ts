import { API } from './../config/api.config';
import { RestService } from './rest.service';
import { Injectable } from '@angular/core';
import { User } from '../models/dto/user';
import {AuthService} from "./auth.service";
@Injectable({
  providedIn: 'root'
})
export class UserService {

  user: User;

  constructor(
    private restService: RestService,
    private authService: AuthService
  ) {
    this.authService.check().then((u: any) => this.user = u);
  }

  post(user: User) {
    return this.restService.post(`${API.endpoint}${API.entities.user}`, user);
  }

  addEventToCurrentUser(id: number) {
    return this.restService.post(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.events}`, [id]);
  }

  removeEventToCurrentUser(id: number) {
    return this.restService.patch(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.events}`, [id]);
  }

  addCitiesToCurrentUser(citiesToAdd: String[]) {
    return this.restService.post(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.cities}`, citiesToAdd);
  }

  removeCitiesToCurrentUser(citiesToDelete: String[]) {
    return this.restService.patch(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.cities}`, citiesToDelete);
  }

  addDepartmentsToCurrentUser(departmentsToAdd: String[]) {
    return this.restService.post(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.departments}`, departmentsToAdd);
  }

  removeDepartmentsToCurrentUser(departmentsToDelete: String[]) {
    return this.restService.patch(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.departments}`, departmentsToDelete);
  }

  addRegionsToCurrentUser(regionsToAdd: String[]) {
    return this.restService.post(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.regions}`, regionsToAdd);
  }

  removeRegionsToCurrentUser(regionsToDelete: String[]) {
    return this.restService.patch(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.regions}`, regionsToDelete);
  }

  addActivitiesToCurrentUser(activitiesToAdd: String[]) {
    return this.restService.post(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.activities}`, activitiesToAdd);
  }

  removeActivitiesToCurrentUser(activitiesToDelete: String[]) {
    return this.restService.patch(`${API.endpoint}${API.entities.user}/${this.user.id}${API.entities.activities}`, activitiesToDelete);
  }
}
