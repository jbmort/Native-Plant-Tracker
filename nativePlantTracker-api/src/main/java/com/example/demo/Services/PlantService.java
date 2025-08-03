package com.example.demo.Services;

import com.example.demo.DTO.PlantDto;
import com.example.demo.Entities.Garden;
import com.example.demo.Entities.Plant;

import java.util.List;

public interface PlantService {
    public Plant getPlant(long id);
    public Plant getPlantByName(String username);
    public List<Plant> getAllPlants();
    public List<Garden> getAllGardensForPlant(long plantId);
    public Plant addPlant(PlantDto plant);
    public Plant addPlant(Plant plant);
    public Plant updatePlant(long id, PlantDto plant);
    public void deletePlant(long id);
}
