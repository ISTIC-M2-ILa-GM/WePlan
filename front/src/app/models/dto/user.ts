import {Entity} from "./entity";
import {City} from "./city";
import {Department} from "./department";
import {Region} from "./region";
import {Event} from "./event";
import {Activity} from "./activity";

export interface User extends Entity {
  firstName: string;
  lastName: string;
  email: string;
  role: string;
  cities: Array<City>;
  departments: Array<Department>;
  regions: Array<Region>;
  activities: Array<Activity>;
  events: Array<Event>;
}
