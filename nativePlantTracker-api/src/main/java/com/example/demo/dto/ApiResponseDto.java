package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResponseDto {
    @JsonProperty("plants")
    List<apiResponsePlantDto> plants;

    public List<apiResponsePlantDto> getPlants() {
        return plants;
    }

    public void setPlants(List<apiResponsePlantDto> plants) {
        this.plants = plants;
    }
}
