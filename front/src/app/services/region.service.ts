import {Injectable} from '@angular/core';
import {RestService} from "./rest.service";
import {API} from "../config/api.config";
import {RegionRequest} from "../models/request/region.request";
import {PageRequest} from "../models/request/page.request";

@Injectable({
  providedIn: 'root'
})
export class RegionService {

  static URL = `${API.endpoint}${API.entities.regions}`;

  constructor(private restService: RestService) {
  }

  get(pageRequest: PageRequest) {
    return this.restService.get(`${URL}`, pageRequest);
  }

  getOne(id: number) {
    return this.restService.get(`${URL}/${id}`);
  }

  post(region: RegionRequest) {
    return this.restService.post(`${URL}`, region);
  }

  patch(fields: Map<string, Object>) {
    return this.restService.patch(`${URL}`, fields);
  }

  delete(id: number) {
    return this.restService.delete(`${URL}/${id}`);
  }
}
