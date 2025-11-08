package com.example.demo.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plant {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id
    @Column(unique = true, name = "id")
    private long id;

    @Nullable
    private String commonName;

    @Column(unique = true, nullable = false)
    private String sciName;

    @Nullable
    private String imageUrl;

    private String plantType;
    private Double averageHeight;
//    private String toxicity;
    @Column(columnDefinition = "TEXT")
    private String description;


    // --- Edible Information ---
    private boolean isEdible;
    @ElementCollection
    @CollectionTable(name = "plant_edible_parts", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "part")
    private List<String> edibleParts = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "plant_flower_colors", joinColumns = @JoinColumn(name = "plant_id"))
//    @Column(name = "color")
//    private List<String> flowerColors = new ArrayList<>();

    public void setLightRequirement(List<String> lightRequirement) {
        this.lightRequirement = lightRequirement;
    }

    //    @Column(name = "ph_min")
//    private Double phMinimum;
//    private Double phMaximum;
    @ElementCollection
    @CollectionTable(name="light_requirement", joinColumns = @JoinColumn(name="plant_id"))
    @Column(name="light_requirement")
    private List<String> lightRequirement = new ArrayList<>();

    public void setSoil_moisture(List<String> soil_moisture) {
        this.soil_moisture = soil_moisture;
    }

    public List<String> getLightRequirement() {
        return lightRequirement;
    }

    @ElementCollection
    @CollectionTable(name="moisture_requirement", joinColumns = @JoinColumn(name="plant_id"))
    @Column(name="moisture_requirement")
    private List<String> soil_moisture = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "plant_bloom_months", joinColumns = @JoinColumn(name = "plant_id"))
//    @Column(name = "month")
//    private List<String> bloomMonths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "plant_native_zones", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "zone_name")
    private List<String> nativeZones = new ArrayList<>();



    @Nullable
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(@Nullable String common_name) {
        this.commonName = common_name;
    }


    public String getSciName() {
        return sciName;
    }

    public void setSciName(String sci_name) {
        this.sciName = sci_name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public Double getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(Double averageHeight) {
        this.averageHeight = averageHeight;
    }


    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public List<String> getEdibleParts() {
        return edibleParts;
    }

    public void setEdibleParts(List<String> edibleParts) {
        this.edibleParts = edibleParts;
    }

    public List<String> getNativeZones() {
        return nativeZones;
    }

    public void setNativeZones(List<String> nativeZones) {
        this.nativeZones = nativeZones;
    }

    public String summary(){
        String mainString = "This plant is commonly called " + this.getCommonName() + ".";
        if(this.getDescription() != null && !this.getDescription().isEmpty()){
            return mainString + " Description: " + this.getDescription();
        }else{
            return mainString;
        }
    }

}
