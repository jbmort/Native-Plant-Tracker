package com.example.demo.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GRASS")
public class Grass extends Plant {

    @Override
    public String summary() {
        String mainstring = "This grass is commonly called " + this.getCommonName();
        if(this.getDescription() != null && !this.getDescription().isEmpty()){
            mainstring += " Description: " + this.getDescription();
        }
        return mainstring;
    }
}
