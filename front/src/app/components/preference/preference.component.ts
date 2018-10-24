import {Component, OnInit} from '@angular/core';
import {City} from '../../models/dto/city';
import {CityService} from "../../services/city.service";
import {PageResponse} from "../../models/response/page.response";
import {Department} from "../../models/dto/department";
import {Region} from "../../models/dto/region";
import {Activity} from "../../models/dto/activity";
import {RegionService} from "../../services/region.service";
import {DepartmentService} from "../../services/department.service";
import {ActivityService} from "../../services/activity.service";

@Component({
  selector: 'app-preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.css']
})
export class PreferenceComponent implements OnInit {

  public allCities: Array<City> = [];
  public allDepartments: Array<Department> = [];
  public allRegions: Array<Region> = [];
  public allActivities: Array<Activity> = [];

  cityNames(): Array<string> {
    return this.allCities.map(c => c.name);
  }

  departmentNames(): Array<string> {
    return this.allDepartments.map(c => c.name);
  }

  regionNames(): Array<string> {
    return this.allRegions.map(c => c.name);
  }

  activityNames: Materialize.AutoCompleteOptions = {
    data: ["abcd"]
  };

  constructor(
    private cityService: CityService,
    private departmentService: DepartmentService,
    private regionService: RegionService,
    private activityService: ActivityService
  ) {
  }

  ngOnInit() {
    this.cityService.get().subscribe((c: PageResponse<City>) => {
      this.allCities = c.results;
    });
    this.departmentService.get().subscribe((c: PageResponse<Department>) => {
      this.allDepartments = c.results;
    });
    this.regionService.get().subscribe((c: PageResponse<Region>) => {
      this.allRegions = c.results;
    });
    this.activityService.get().subscribe((c: PageResponse<Activity>) => {
      this.allActivities = c.results;
    });
  }

}
