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
import {User} from "../../models/dto/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.css']
})
export class PreferenceComponent implements OnInit {

  allCities: Array<City> = [];
  allDepartments: Array<Department> = [];
  allRegions: Array<Region> = [];
  allActivities: Array<Activity> = [];
  user: User;

  subscribedCities: Array<City> = [];
  subscribedDepartments: Array<Department> = [];
  subscribedRegions: Array<Region> = [];
  subscribedActivities: Array<Activity> = [];

  constructor(
    private cityService: CityService,
    private departmentService: DepartmentService,
    private regionService: RegionService,
    private activityService: ActivityService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.cityService.get().subscribe((c: PageResponse<City>) => {
      this.allCities = c.results.sort((c1, c2) => c1.name.substr(0, 1) < c2.name.substr(0, 1) ? 0 : 1);
    });
    this.departmentService.get().subscribe((c: PageResponse<Department>) => {
      this.allDepartments = c.results.sort((c1, c2) => c1.name.substr(0, 1) < c2.name.substr(0, 1) ? 0 : 1);
    });
    this.regionService.get().subscribe((c: PageResponse<Region>) => {
      this.allRegions = c.results.sort((c1, c2) => c1.name.substr(0, 1) < c2.name.substr(0, 1) ? 0 : 1);
    });
    this.activityService.get().subscribe((c: PageResponse<Activity>) => {
      this.allActivities = c.results.sort((c1, c2) => c1.name.substr(0, 1) < c2.name.substr(0, 1) ? 0 : 1);
    });
    this.refreshData();
  }

  private refreshData() {
    this.subscribedCities = [];
    this.subscribedDepartments = [];
    this.subscribedRegions = [];
    this.subscribedActivities = [];
    this.userService.getCurrentUser().subscribe((r) => {
      //TODO userService
      // this.subscribedCities = r.cities;
      // this.subscribedDepartments = r.departments;
      // this.subscribedRegions = r.regions;
      // this.subscribedActivities = r.activities;
    });
  }

  update() {
    this.updateCities();
    this.updateDepartments();
    this.updateRegions();
    this.updateActivities();
  }

  private updateCities() {
    if (this.subscribedCities !== this.user.cities) {
      const currentCities = this.user.cities.map(c => c.id);
      const newCities = this.subscribedCities.map(c => c.id);
      const citiesToAdd = newCities.filter(c => !currentCities.includes(c));
      const citiesToDelete = currentCities.filter(c => !newCities.includes(c));
      this.userService.addCitiesToCurrentUser(citiesToAdd);
      this.userService.removeCitiesToCurrentUser(citiesToDelete);
    }
  }

  private updateDepartments() {
    if (this.subscribedDepartments !== this.user.departments) {
      const currentDepartments = this.user.departments.map(c => c.id);
      const newDepartments = this.subscribedDepartments.map(c => c.id);
      const departmentsToAdd = newDepartments.filter(c => !currentDepartments.includes(c));
      const departmentsToDelete = currentDepartments.filter(c => !newDepartments.includes(c));
      this.userService.addDepartmentsToCurrentUser(departmentsToAdd);
      this.userService.removeDepartmentsToCurrentUser(departmentsToDelete);
    }
  }

  private updateRegions() {
    if (this.subscribedRegions !== this.user.regions) {
      const currentRegions = this.user.regions.map(c => c.id);
      const newRegions = this.subscribedRegions.map(c => c.id);
      const regionsToAdd = newRegions.filter(c => !currentRegions.includes(c));
      const regionsToDelete = currentRegions.filter(c => !newRegions.includes(c));
      this.userService.addRegionsToCurrentUser(regionsToAdd);
      this.userService.removeRegionsToCurrentUser(regionsToDelete);
    }
  }

  private updateActivities() {
    if (this.subscribedActivities !== this.user.activities) {
      const currentActivities = this.user.activities.map(c => c.id);
      const newActivities = this.subscribedActivities.map(c => c.id);
      const activitiesToAdd = newActivities.filter(c => !currentActivities.includes(c));
      const activitiesToDelete = currentActivities.filter(c => !newActivities.includes(c));
      this.userService.addActivitiesToCurrentUser(activitiesToAdd);
      this.userService.removeActivitiesToCurrentUser(activitiesToDelete);
    }
  }

  cancel() {
    this.refreshData();
  }
}
