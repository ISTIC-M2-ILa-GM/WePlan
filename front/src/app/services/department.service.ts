import {Injectable} from '@angular/core';
import {API} from "../config/api.config";
import {PageRequest} from "../models/request/page.request";
import {RegionRequest} from "../models/request/region.request";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  static URL = `${API.endpoint}${API.entities.department}`;

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
    const headers = DepartmentService.defaultHeaders(null);
    return this.httpClient.get(`${DepartmentService.URL}`, {headers});
  }

  getOne(id: number) {
    return this.httpClient.get(`${DepartmentService.URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.httpClient.post(`${DepartmentService.URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.httpClient.patch(`${DepartmentService.URL}`, fields);
  }

  delete(id: number) {
    return this.httpClient.delete(`${DepartmentService.URL}/${id}`);
  }
}
