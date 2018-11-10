import {Injectable} from '@angular/core';
import {API} from "../config/api.config";
import {PageRequest} from "../models/request/page.request";
import {RegionRequest} from "../models/request/region.request";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CityService {

  static URL = `${API.endpoint}${API.entities.city}`;

  static defaultHeaders(options) {
    let {headers = new HttpHeaders()} = options != null ? options : {};
    headers.set('Content-Type', 'application/json');
    return headers;
  }

  private httpClient: HttpClient = null;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  get(pageRequest?: PageRequest) {
    const headers = CityService.defaultHeaders(null);
    return this.httpClient.get(`${CityService.URL}`, {headers});
  }

  getOne(id: number) {
    return this.httpClient.get(`${CityService.URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.httpClient.post(`${CityService.URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.httpClient.patch(`${CityService.URL}`, fields);
  }

  delete(id: number) {
    return this.httpClient.delete(`${CityService.URL}/${id}`);
  }
}
