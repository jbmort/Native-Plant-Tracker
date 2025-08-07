export class PlantReport {
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

    private _instances: number = 0;
    public get instances(): number {
        return this._instances;
    }
    public set instances(value: number) {
        this._instances = value;
    }

    private _years_present: number = 0;
    public get years_present(): number {
        return this._years_present;
    }
    public set years_present(value: number) {
        this._years_present = value;
    }
}
