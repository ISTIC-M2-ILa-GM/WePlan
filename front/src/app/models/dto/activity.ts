import {Entity} from "./entity";
import {City} from "./city";

export interface Activity extends Entity {
  name: string;
  cost: number;
  outDoor: boolean;
  activityType: string;
  cities: Array<City>;
}
