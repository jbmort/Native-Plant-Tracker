package com.example.demo.services;

import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantTypeDto;
import com.example.demo.entities.*;
import com.example.demo.repository.PlantRepository;
import com.example.demo.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    final PlantRepository plantRepository;
    final TypesRepository typesRepository;

    @Autowired
    PlantServiceImpl(PlantRepository plantRepository, TypesRepository typesRepository) {
        this.plantRepository = plantRepository;
        this.typesRepository = typesRepository;
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
            PlantType plantType = new PlantType();
            Plant newPlant;
            if(typesRepository.findById(plant.getType()).isPresent()){
                plantType = typesRepository.findById(plant.getType()).get();
            }

            switch (plantType.getValue()) {
                case "FORB":
                case "FLOWERING PERENNIAL":
                    Forb newForb = new Forb();
                    newForb.setFlowerColor(plant.getFlowerColor());
                    newPlant = newForb;
                    break;

                case "GRASS":
                case "NATIVE GRASS":
                    newPlant = new Grass();
                    break;

                case "TREE":
                    newPlant = new Tree();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported plant type: " + plantType.getName());
            }

            newPlant.setCommonName(plant.getCommon_name());
            newPlant.setDescription(plant.getDescription());
            newPlant.setSciName(plant.getSci_name());
            newPlant.setPlantType(plantType);
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
        PlantType plantType = null;
        if(typesRepository.findById(plant.getType()).isPresent()){
            plantType = typesRepository.findById(plant.getType()).get();
            if(plantType.getValue().equals("FORB")){
                Forb forb = new Forb(updatedPlant);
                forb.setFlowerColor(plant.getFlowerColor());
                updatedPlant = forb;
            }
        };

        updatedPlant.setDescription(plant.getDescription());
        updatedPlant.setSciName(plant.getSci_name());
        updatedPlant.setCommonName(plant.getCommon_name());
        updatedPlant.setPlantType(plantType);
        return plantRepository.save(updatedPlant);
    }

    @Override
    public void deletePlant(long id) {
        Plant plant = plantRepository.getPlantById(id);
        plantRepository.delete(plant);
    }

    @Override
    public List<PlantType> getTypes() {
        return typesRepository.findAll();
    }

    @Override
    public PlantType addPlantType(PlantTypeDto plantTypeDto) {
        PlantType typeToAdd = new PlantType();
        typeToAdd.setName(plantTypeDto.getName());
        typeToAdd.setValue(plantTypeDto.getValue());
        return typesRepository.save(typeToAdd);
    }


}
