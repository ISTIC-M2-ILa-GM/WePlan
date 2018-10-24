import {Injectable} from '@angular/core';
import {RestService} from "./rest.service";
import {API} from "../config/api.config";
import {RegionRequest} from "../models/request/region.request";
import {PageRequest} from "../models/request/page.request";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {hash} from "tweetnacl";

@Injectable({
  providedIn: 'root'
})
export class RegionService {

  static URL = `${API.endpoint}${API.entities.region}`;

  static defaultHeaders(options) {
    let {headers = new HttpHeaders()} = options != null ? options : {};
    headers.set('Content-Type', 'application/json');
    return headers;
  }

  constructor(private httpClient: HttpClient) {
  }

  get(pageRequest?: PageRequest) {
    const headers = RegionService.defaultHeaders(null);
    return this.httpClient.get(`${RegionService.URL}`, {headers});
  }

  getOne(id: number) {
    return this.httpClient.get(`${RegionService.URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.httpClient.post(`${RegionService.URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.httpClient.patch(`${RegionService.URL}`, fields);
  }

  delete(id: number) {
    return this.httpClient.delete(`${RegionService.URL}/${id}`);
  }
}
