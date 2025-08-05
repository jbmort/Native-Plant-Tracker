import { Garden } from "./garden";

export class Plant {
    constructor(){}
    id: number = 0;
    name: string = '';
    sci_name: string = '';
    description: string = '';
    created_on: Date = new Date;
    gardens: Array<Garden> = new Array;
}
