package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FORB")
public class Forb extends Plant {

    @Column(name = "flower_color")
    private String flowerColor;

    public Forb() {}


    public Forb(Plant updatedPlant) {
        super();
        this.setId(updatedPlant.getId());
        this.setPlantType(updatedPlant.getPlantType());
        this.setCommonName(updatedPlant.getCommonName());
        this.setDescription(updatedPlant.getDescription());
        this.setSciName(updatedPlant.getSciName());
        this.setCreated_on(updatedPlant.getCreated_on());
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    @Override
    public String summary() {
        String mainstring = super.summary();
        if(flowerColor != null){
            mainstring += " Flower Color: " + this.getFlowerColor();
        }
        return mainstring;
    }
}
