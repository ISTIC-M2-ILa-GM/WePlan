import {Entity} from "./entity";
import {Region} from "./region";

export interface Department extends Entity {
  name: string;
  code: number;
  departments: Region;
}
