import {Injectable} from '@angular/core';
import {RestService} from "./rest.service";
import {UserRequest} from "../models/dto/user.request";
import {Observable} from "rxjs";
import {HttpHeaders} from "@angular/common/http";
import {LoginRequest} from "../models/dto/login.request";
import {API} from "../config/api.config";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private restService: RestService) { }


  register(user: UserRequest): Observable<ArrayBuffer> {
    return this.restService.post(`${API.endpoint}${API.entities.users}`, user);
  }

  login(login: LoginRequest) {
    let body = new URLSearchParams();
    body.set('email', login.email);
    body.set('password', login.password);

    const options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.restService.post(`/backend/login`, body.toString(), options);
  }
}
