export class GardenReport {
    private _id: number = 0;
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    private _name: String = '';
    public get name(): String {
        return this._name;
    }
    public set name(value: String) {
        this._name = value;
    }
    private _description: String = '';
    public get description(): String {
        return this._description;
    }
    public set description(value: String) {
        this._description = value;
    }
    private _num_Plants: number = 0;
    public get num_Plants(): number {
        return this._num_Plants;
    }
    public set num_Plants(value: number) {
        this._num_Plants = value;
    }
    private _age: number = 0;
    public get age(): number {
        return this._age;
    }
    public set age(value: number) {
        this._age = value;
    }
}
