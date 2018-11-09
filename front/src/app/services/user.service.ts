import { API } from './../config/api.config';
import { RestService } from './rest.service';
import { Injectable } from '@angular/core';
import { User } from '../models/dto/user';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private restService: RestService) { }

  post(user: User) {
    return this.restService.post(`${API.endpoint}${API.entities.user}`, user);
  }

  addEventToCurrentUser(id: number) {
    // TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.events}`, [id]);
  }

  removeEventToCurrentUser(id: number) {
    // TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.events}`, [id]);
  }

  addCitiesToCurrentUser(citiesToAdd: number[]) {
    // TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.cities}`, citiesToAdd);
  }

  removeCitiesToCurrentUser(citiesToDelete: number[]) {
    // TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.cities}`, citiesToDelete);
  }

  addDepartmentsToCurrentUser(departmentsToAdd: number[]) {
    //TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.departments}`, departmentsToAdd);
  }

  removeDepartmentsToCurrentUser(departmentsToDelete: number[]) {
    //TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.departments}`, departmentsToDelete);
  }

  addRegionsToCurrentUser(regionsToAdd: number[]) {
    //TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.regions}`, regionsToAdd);
  }

  removeRegionsToCurrentUser(regionsToDelete: number[]) {
    //TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.regions}`, regionsToDelete);
  }

  addActivitiesToCurrentUser(activitiesToAdd: number[]) {
    //TODO where is current user ?
    return this.restService.post(`${API.endpoint}${API.entities.user}/0/${API.entities.activities}`, activitiesToAdd);
  }

  removeActivitiesToCurrentUser(activitiesToDelete: number[]) {
    //TODO where is current user ?
    return this.restService.delete(`${API.endpoint}${API.entities.user}/0/${API.entities.activities}`, activitiesToDelete);
  }
}
