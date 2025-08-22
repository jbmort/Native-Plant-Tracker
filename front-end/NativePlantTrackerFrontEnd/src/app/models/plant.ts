import { Garden } from "./garden";

export class Plant {
    constructor(){}
    id: number = 0;
    commonName: string = '';
    sciName: string = '';
    description: string = '';
    flowerColor: string | null = null;
    type: number = 0;
    created_on: Date = new Date;
    gardens: Array<Garden> = new Array;
}
