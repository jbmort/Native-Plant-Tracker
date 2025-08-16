package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "plant_category", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PLANT")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String commonName;

    @Nullable
    private String sciName;

    @Nullable
    private String description;

    @NotNull
    private LocalDateTime created_on;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_type_id", nullable = false)
    @JsonBackReference
    private PlantType plantType;

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String common_name) {
        this.commonName = common_name;
    }

    @Nullable
    public String getSciName() {
        return sciName;
    }

    public void setSciName(@Nullable String sci_name) {
        this.sciName = sci_name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDateTime created_on) {
        this.created_on = created_on;
    }

    public String summary(){
        String mainString = "This plant is commonly called " + this.getCommonName() + ".";
        if(this.getDescription() != null && !this.getDescription().isEmpty()){
            return mainString + " Description: " + this.getDescription();
        }else{
            return mainString;
        }
    }

//    public List<Garden> getGardenList() {
//        return gardenList;
//    }
//
//    public void setGardenList(List<Garden> gardenList) {
//        this.gardenList = gardenList;
//    }
}
