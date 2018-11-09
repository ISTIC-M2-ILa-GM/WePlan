import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private httpClient: HttpClient) { }

  get(url, options?) { return this.httpClient.get(url, options); }

  put(url, body, options?) { return this.httpClient.put(url, body, options); }

  post(url, body, options?) { return this.httpClient.post(url, body, options); }

  patch(url, body, options?) { return this.httpClient.post(url, body, options); }

  delete(url, options?) { return this.httpClient.delete(url, options); }
}
