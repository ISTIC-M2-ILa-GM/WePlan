import {Entity} from "./entity";
import {Activity} from "./activity";
import {City} from "./city";

export interface Event extends Entity {
  canceled: boolean;
  date: Date;
  activity: Activity;
  city: City;
}
