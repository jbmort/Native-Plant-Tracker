import { Plant } from "./plant";

export class Garden {
    constructor(){}
    id: number = 0;
    name: String = '';
    description: String = '';
    created_on: Date = new Date;
    plantList: Array<Plant> = new Array;
}
