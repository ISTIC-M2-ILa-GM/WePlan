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
import {AuthService} from "../../services/auth.service";

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

  subscribedCities: Array<string> = [];
  subscribedDepartments: Array<string> = [];
  subscribedRegions: Array<string> = [];
  subscribedActivities: Array<string> = [];

  constructor(
    private cityService: CityService,
    private departmentService: DepartmentService,
    private regionService: RegionService,
    private activityService: ActivityService,
    private userService: UserService,
    private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.authService.check().then((u: User) => {
      this.user = u;
      this.initData();
    });
  }

  private initData() {
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
    this.subscribedCities = this.user.cities.map(c => c.id.toString());
    this.subscribedDepartments = this.user.departments.map(c => c.id.toString());
    this.subscribedRegions = this.user.regions.map(c => c.id.toString());
    this.subscribedActivities = this.user.activities.map(c => c.id.toString());
  }

  update() {
    this.updateCities();
    this.updateDepartments();
    this.updateRegions();
    this.updateActivities();
  }

  private updateCities() {
    const currentCities = this.user.cities.map(c => c.id.toString());
    if (this.subscribedCities !== currentCities) {
      const toAdd = this.subscribedCities.filter(c => !currentCities.includes(c));
      const toDelete = currentCities.filter(c => !this.subscribedCities.includes(c));
      if (toAdd.length > 0) {
        this.userService.addCitiesToCurrentUser(toAdd).subscribe((u: any) => this.user = u);
      }
      if (toDelete.length > 0) {
        this.userService.removeCitiesToCurrentUser(toDelete).subscribe((u: any) => this.user = u);
      }
    }
  }

  private updateDepartments() {
    const currentDepartments = this.user.departments.map(c => c.id.toString());
    if (this.subscribedDepartments !== currentDepartments) {
      const toAdd = this.subscribedDepartments.filter(c => !currentDepartments.includes(c));
      const toDelete = currentDepartments.filter(c => !this.subscribedDepartments.includes(c));
      if (toAdd.length > 0) {
        this.userService.addDepartmentsToCurrentUser(toAdd).subscribe((u: any) => this.user = u);
      }
      if (toDelete.length > 0) {
        this.userService.removeDepartmentsToCurrentUser(toDelete).subscribe((u: any) => this.user = u);
      }
    }
  }

  private updateRegions() {
    const currentRegions = this.user.regions.map(c => c.id.toString());
    if (this.subscribedRegions !== currentRegions) {
      const toAdd = this.subscribedRegions.filter(c => !currentRegions.includes(c));
      const toDelete = currentRegions.filter(c => !this.subscribedRegions.includes(c));
      if (toAdd.length > 0) {
        this.userService.addRegionsToCurrentUser(toAdd).subscribe((u: any) => this.user = u);
      }
      if (toDelete.length > 0) {
        this.userService.removeRegionsToCurrentUser(toDelete).subscribe((u: any) => this.user = u);
      }
    }
  }

  private updateActivities() {
    const currentActivities = this.user.activities.map(c => c.id.toString());
    if (this.subscribedActivities !== currentActivities) {
      console.log(this.subscribedActivities);
      console.log(currentActivities);
      const toAdd = this.subscribedActivities.filter(c => !currentActivities.includes(c));
      const toDelete = currentActivities.filter(c => !this.subscribedActivities.includes(c));
      if (toAdd.length > 0) {
        this.userService.addActivitiesToCurrentUser(toAdd).subscribe((u: any) => this.user = u);
      }
      if (toDelete.length > 0) {
        this.userService.removeActivitiesToCurrentUser(toDelete).subscribe((u: any) => this.user = u);
      }
    }
  }

  cancel() {
    this.refreshData();
  }
}
