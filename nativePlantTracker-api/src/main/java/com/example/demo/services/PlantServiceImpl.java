package com.example.demo.services;

import com.example.demo.dto.PlantDto;
import com.example.demo.entities.Garden;
import com.example.demo.entities.Plant;
import com.example.demo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Plant addPlant(Plant plant) {
        if(!plantRepository.existsPlantByCommonName(plant.getCommonName())
                && !plantRepository.existsPlantBySciName(plant.getSciName()) ){
            return plantRepository.save(plant);
        }
        return plantRepository.getPlantByCommonName(plant.getCommonName());
    }

    public Plant updatePlant(long id, PlantDto plant) {
        Plant updatedPlant = plantRepository.getPlantById(id);
        updatedPlant.setDescription(plant.getDescription());
        updatedPlant.setSciName(plant.getSci_name());
        updatedPlant.setCommonName(plant.getCommon_name());
        return plantRepository.save(updatedPlant);
    }

    public void deletePlant(long id) {
        Plant plant = plantRepository.getPlantById(id);
        plantRepository.delete(plant);
    }

}
