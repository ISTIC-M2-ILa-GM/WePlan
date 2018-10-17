import {Component, OnInit} from '@angular/core';
import {City} from '../../models/dto/city';
import {CityService} from "../../services/city.service";

@Component({
  selector: 'app-preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.css']
})
export class PreferenceComponent implements OnInit {
  private cities: Array<City>;

  constructor(private cityService: CityService) {
  }

  ngOnInit() {
    this.cityService.get().subscribe((response) => console.log(response));
  }

}
