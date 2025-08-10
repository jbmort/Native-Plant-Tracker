export class GardenReport {
    public id: number = 0;
    public getId(): number {
        return this.id;
    }
    public setId(value: number) {
        this.id = value;
    }

    public garden_name: String = '';
    public getGarden_name(): String {
        return this.garden_name;
    }
    public setGarden_name(value: String) {
        this.garden_name = value;
    }
  

    public description: String = '';
    public getDescription(): String {
        return this.description;
    }
    public setDescription(value: String) {
        this.description = value;
    }

    public num_plants: number = 0;
    public getNum_plants(): number {
        return this.num_plants;
    }
    public setNum_plants(value: number) {
        this.num_plants = value;
    }
 
    public age: number = 0;
    public getAge(): number {
        return this.age;
    }
    public setAge(value: number) {
        this.age = value;
    }

}
