import {Injectable} from '@angular/core';
import {API} from "../config/api.config";
import {PageRequest} from "../models/request/page.request";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  static URL = `${API.endpoint}${API.entities.event}`;

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
    const headers = EventService.defaultHeaders(null);
    return this.httpClient.get(`${EventService.URL}`, {headers});
  }
}
