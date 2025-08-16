export class PlantTypeDto {
    private name: String = '';
    private value: String = '';

    getName(): String{
        return this.name;
    }

    setName(name: String){
        this.name = name;
    }

    setValue(value: String){
        this.value = value;
    }

    getValue(){
        return this.value;
    }
}
