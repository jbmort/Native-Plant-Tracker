package com.example.demo.Services;

import com.example.demo.DTO.GardenDto;
import com.example.demo.DTO.GardenReportDto;
import com.example.demo.DTO.PlantDto;
import com.example.demo.DTO.PlantReportDTO;
import com.example.demo.Entities.Garden;
import com.example.demo.Entities.Plant;

import java.util.List;

public interface GardenService {

    public Garden getGarden(long id);
    public List<Garden> getGardens();
    public List<Plant> getAllPlantsForGarden(long gardenId, String currentUsername);
    public void addGarden(GardenDto garden);
    public Plant addPlantToGarden(PlantDto plant, long gardenId, String currentUsername);
    public void deleteGarden(long id, String username);
    public void deletePlantFromGarden(long plantId, long gardenId);
    public Garden updateGarden(long gardenId, GardenDto gardenDto, String currentUsername);
    public List<Garden> findGardensByUsername(String currentUsername);
    Garden findGardenByIdAndUsername(long gardenId, String username);

    Garden createGardenForUser(GardenDto newGardenDto, String currentUsername);

    Plant getPlantById(long plantId, long gardenId, String username);

    List<PlantReportDTO> getPlantReport(String Username);

    List<GardenReportDto> getGardenReport(String Username);
}
