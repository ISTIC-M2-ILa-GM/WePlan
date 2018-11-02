import {Entity} from "./entity";
import {Department} from "./department";

export interface City extends Entity {
  name: string;
  postalCode: number;
  department: Department;
}
