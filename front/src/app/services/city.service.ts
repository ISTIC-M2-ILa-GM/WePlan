import { Injectable } from '@angular/core';
import {API} from "../config/api.config";
import {RestService} from "./rest.service";
import {PageRequest} from "../models/request/page.request";
import {RegionRequest} from "../models/request/region.request";
import {HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CityService {

  static URL = `${API.endpoint}${API.entities.city}`;

  constructor(private restService: RestService) {
  }

  get(pageRequest?: PageRequest) {
    const options = {
      headers: new HttpHeaders().set("Content-Type", "application/json"),
      responseType: 'PageResponse<City>'
    };
    return this.restService.get(`${CityService.URL}`, options);
  }

  getOne(id: number) {
    return this.restService.get(`${CityService.URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.restService.post(`${CityService.URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.restService.patch(`${CityService.URL}`, fields);
  }

  delete(id: number) {
    return this.restService.delete(`${CityService.URL}/${id}`);
  }
}
