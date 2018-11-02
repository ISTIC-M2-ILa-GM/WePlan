import {Injectable} from '@angular/core';
import {API} from "../config/api.config";
import {PageRequest} from "../models/request/page.request";
import {RegionRequest} from "../models/request/region.request";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  static URL = `${API.endpoint}${API.entities.activity}`;

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
    const headers = ActivityService.defaultHeaders(null);
    return this.httpClient.get(`${ActivityService.URL}`, {headers});
  }

  getOne(id: number) {
    return this.httpClient.get(`${ActivityService.URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.httpClient.post(`${ActivityService.URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.httpClient.patch(`${ActivityService.URL}`, fields);
  }

  delete(id: number) {
    return this.httpClient.delete(`${ActivityService.URL}/${id}`);
  }
}
