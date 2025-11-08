package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.GardenReportDto;
import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantReportDTO;
import com.example.demo.entities.Garden;
import com.example.demo.entities.Plant;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface GardenService {


    List<Plant> allPlantsForUser(String username);

    Garden getGarden(long id);
    List<Garden> getGardens();
    List<PlantDto> getAllPresentPlantsForGarden(long gardenId, String currentUsername);
    void addGarden(GardenDto garden);
//    Plant addPlantToGarden(PlantDto plant, long gardenId, String currentUsername, LocalDate dateAdded);

    @Transactional
    Plant addPlantToGarden(long plantId, long gardenId, String currentUsername, LocalDate dateAdded);

    void deleteGarden(long id, String username);
//    void deletePlantFromGarden(long plantId, long gardenId, LocalDate date);
    Garden updateGarden(long gardenId, GardenDto gardenDto, String currentUsername);
    List<Garden> findGardensByUsername(String currentUsername);

    @Transactional
    void deletePlantFromGarden(long gardenId, long plantId, LocalDate date);

    Garden findGardenByIdAndUsername(long gardenId, String username);

    Garden createGardenForUser(GardenDto newGardenDto, String currentUsername);

    Plant getPlantById(long plantId, long gardenId, String username);

    List<PlantReportDTO> getPlantReport(String Username);

    List<GardenReportDto> getGardenReport(String Username);
}
