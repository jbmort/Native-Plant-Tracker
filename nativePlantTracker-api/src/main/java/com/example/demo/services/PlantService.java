package com.example.demo.services;

import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantTypeDto;
import com.example.demo.dto.apiResponsePlantDto;
import com.example.demo.entities.Plant;

import java.util.List;

public interface PlantService {
    Plant getPlant(long id);
    Plant getPlantByName(String username);
    List<Plant> getAllPlants();
    Plant addPlant(apiResponsePlantDto plant);

//    Plant addPlant(Plant plant);
    Plant updatePlant(long id, PlantDto plant);
    void deletePlant(long id);
    public List<Plant> findPlantsByName(String name);

    List<Plant> getPlantsByName(String name);
}
