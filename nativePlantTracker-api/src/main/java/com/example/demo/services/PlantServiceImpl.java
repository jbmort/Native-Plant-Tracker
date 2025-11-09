package com.example.demo.services;

import com.example.demo.dto.PlantDataDto;
import com.example.demo.dto.PlantDto;
import com.example.demo.dto.apiResponsePlantDto;
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
    public Plant addPlant(apiResponsePlantDto plant) {
        System.out.println("Adding plant " + plant);
        if(!plantRepository.existsById((plant.id())) ){
            Plant newPlant = new Plant();

            newPlant.setCommonName(plant.name());
            newPlant.setDescription(plant.description());
            newPlant.setSciName(plant.scientificName());
            newPlant.setPlantType(plant.type());
            newPlant.setId(plant.id());
            newPlant.setImageUrl(plant.images().thumb());

            List<String> edibleParts = plant.data().stream()
                    .filter(d -> d.key().equals("edible parts"))
                    .map(PlantDataDto::value)
                    .toList();

            newPlant.setEdibleParts(edibleParts);
           return plantRepository.save(newPlant);
        }
        return plantRepository.findById(plant.id()).orElseThrow(() -> new RuntimeException("Plant with id " + plant.id() + " not found in the database before trying to add it"));
    }

//    @Override
//    public Plant addPlant(Plant plant) {
//        if(!plantRepository.existsPlantByCommonName(plant.getCommonName())
//                && !plantRepository.existsPlantBySciName(plant.getSciName()) ){
//            return plantRepository.save(plant);
//        }
//        return plantRepository.getPlantByCommonName(plant.getCommonName());
//    }

    @Override
    public Plant updatePlant(long id, PlantDto plant) {
        Plant updatedPlant = plantRepository.getPlantById(id);

        updatedPlant.setDescription(plant.getDescription());
        updatedPlant.setSciName(plant.getScientificName());
        updatedPlant.setCommonName(plant.getCommonName());
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
