import {Entity} from "./entity";
import {City} from "./city";

export interface Activity extends Entity {
  name: string;
  cost: number;
  outDoor: boolean;
  ActivityType: string;
  cities: Array<City>
}
