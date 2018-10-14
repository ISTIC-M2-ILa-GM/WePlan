import { Injectable } from '@angular/core';
import { User } from './../models/user';

const FAKE_USER = new User('Gautier', 'Rouleau', 'contact@gautier-rouleau.fr');

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor() { }

  get = () => {
    return FAKE_USER;
  }
}
