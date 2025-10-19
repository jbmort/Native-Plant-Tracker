package com.example.demo.services;

import com.example.demo.dto.PlantDto;
import com.example.demo.entities.*;
import com.example.demo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    final PlantRepository plantRepository;

    @Autowired
    PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }


    @Override
    public Plant getPlant(long id) {
        return plantRepository.getPlantById(id);
    }

    @Override
    public Plant getPlantByName(String name) {
        return plantRepository.getPlantByCommonName(name);
    }

    @Override
    public List<Plant> findPlantsByName(String name){
        List<Plant> results = new ArrayList<>();
        results = plantRepository.searchPlantsByCommonNameContainingIgnoreCase(name);
        if(results.isEmpty() || results.size() < 10){
            results.addAll(plantRepository.searchPlantsBySciNameContainingIgnoreCase(name));
        }
        if(results.size() <= 10){
            return results;
        };
        return results.subList(0,10);

    }

    @Override
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }


    @Override
    public Plant addPlant(PlantDto plant) {
        if(!plantRepository.existsPlantByCommonName(plant.getCommon_name())
                && !plantRepository.existsPlantBySciName(plant.getSci_name()) ){
            Plant newPlant = new Plant();

            newPlant.setCommonName(plant.getCommon_name());
            newPlant.setDescription(plant.getDescription());
            newPlant.setSciName(plant.getSci_name());
           return plantRepository.save(newPlant);
        }
        return plantRepository.getPlantByCommonName(plant.getCommon_name());
    }

    @Override
    public Plant addPlant(Plant plant) {
        if(!plantRepository.existsPlantByCommonName(plant.getCommonName())
                && !plantRepository.existsPlantBySciName(plant.getSciName()) ){
            return plantRepository.save(plant);
        }
        return plantRepository.getPlantByCommonName(plant.getCommonName());
    }

    @Override
    public Plant updatePlant(long id, PlantDto plant) {
        Plant updatedPlant = plantRepository.getPlantById(id);

        updatedPlant.setDescription(plant.getDescription());
        updatedPlant.setSciName(plant.getSci_name());
        updatedPlant.setCommonName(plant.getCommon_name());
        return plantRepository.save(updatedPlant);
    }

    @Override
    public void deletePlant(long id) {
        Plant plant = plantRepository.getPlantById(id);
        plantRepository.delete(plant);
    }

    @Override
    public List<Plant> getPlantsByName(String name) {
        List<Plant> commonNameList = plantRepository.searchPlantsByCommonNameContainingIgnoreCase(name);
        List<Plant> sciNameList = plantRepository.searchPlantsBySciNameContainingIgnoreCase(name);

        List<Plant> plantList = new ArrayList<>();
        plantList.addAll(commonNameList);
        plantList.addAll(sciNameList);
        return plantList;
    }


}
