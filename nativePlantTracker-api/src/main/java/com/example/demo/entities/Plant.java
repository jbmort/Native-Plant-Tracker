package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, name = "trefle_id")
    private long trefleId;

    @NotNull
    private String commonName;

    @Nullable
    @Column(unique = true)
    private String sciName;

    @Nullable
    private String imageUrl;

    private String plantType;
    private Double averageHeight;
    private String toxicity;
    private String description;


    // --- Edible Information ---
    private boolean isEdible;
    @ElementCollection
    @CollectionTable(name = "plant_edible_parts", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "part")
    private List<String> edibleParts = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "plant_flower_colors", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "color")
    private List<String> flowerColors = new ArrayList<>();

    @Column(name = "ph_min")
    private Double phMinimum;
    private Double phMaximum;
    private Double lightRequirement;
    private Integer soil_moisture;

    @ElementCollection
    @CollectionTable(name = "plant_bloom_months", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "month")
    private List<String> bloomMonths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "plant_native_zones", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "zone_name")
    private List<String> nativeZones = new ArrayList<>();



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

    public long getTrefleId() {
        return trefleId;
    }

    public void setTrefleId(long trefleId) {
        this.trefleId = trefleId;
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

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
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

    public List<String> getFlowerColors() {
        return flowerColors;
    }

    public void setFlowerColors(List<String> flowerColors) {
        this.flowerColors = flowerColors;
    }

    public Double getPhMinimum() {
        return phMinimum;
    }

    public void setPhMinimum(Double phMinimum) {
        this.phMinimum = phMinimum;
    }

    public Double getPhMaximum() {
        return phMaximum;
    }

    public void setPhMaximum(Double phMaximum) {
        this.phMaximum = phMaximum;
    }

    public Double getLightRequirement() {
        return lightRequirement;
    }

    public void setLightRequirement(Double lightRequirement) {
        this.lightRequirement = lightRequirement;
    }

    public Integer getSoil_moisture() {
        return soil_moisture;
    }

    public void setSoil_moisture(Integer soil_moisture) {
        this.soil_moisture = soil_moisture;
    }

    public List<String> getBloomMonths() {
        return bloomMonths;
    }

    public void setBloomMonths(List<String> bloomMonths) {
        this.bloomMonths = bloomMonths;
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

//    public List<Garden> getGardenList() {
//        return gardenList;
//    }
//
//    public void setGardenList(List<Garden> gardenList) {
//        this.gardenList = gardenList;
//    }
}
