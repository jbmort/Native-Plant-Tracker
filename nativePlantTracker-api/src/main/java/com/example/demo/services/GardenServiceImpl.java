package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.GardenReportDto;
import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantReportDTO;
import com.example.demo.entities.Garden;
import com.example.demo.entities.Plant;
import com.example.demo.entities.User;
import com.example.demo.repository.GardenRepository;
import com.example.demo.repository.PlantRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GardenServiceImpl implements GardenService {

    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    GardenServiceImpl(GardenRepository gardenRepository, PlantRepository plantRepository, UserService userService, UserRepository userRepository) {
        this.gardenRepository = gardenRepository;
        this.plantRepository = plantRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Garden> getGardens() {
        return gardenRepository.findAll();
    }

    @Override
    public List<Plant> getAllPlantsForGarden(long gardenId, String currentUsername) {
        Garden garden = findGardenByIdAndUsername(gardenId, currentUsername);

        return garden.getPlantList();
    }


    @Override
    public Garden getGarden(long gardenId) {
        Optional<Garden> garden = gardenRepository.findById(gardenId);
        return garden.orElse(null);
    }

    @Override
    public void addGarden(GardenDto garden) {
        if (garden != null) {
            Garden newGarden = new Garden();
            newGarden.setName(garden.getName());
            newGarden.setDescription(garden.getDescription());
            gardenRepository.save(newGarden);
        }

    }

    @Override
    @Transactional
    public Plant addPlantToGarden(PlantDto plant, long gardenId, String currentUsername) {
        Plant newPlant = new Plant();
        Garden garden = findGardenByIdAndUsername(gardenId, currentUsername);
        if(garden.getUser().getUsername().equals(currentUsername)) {
            if (!plantRepository.existsPlantByCommonName(plant.getCommon_name())) {
                newPlant.setCommonName(plant.getCommon_name());
                newPlant.setDescription(plant.getDescription());
                newPlant.setSciName(plant.getSci_name());
                plantRepository.save(newPlant);
            }
            newPlant = plantRepository.getPlantByCommonName(plant.getCommon_name());

            if (gardenRepository.findById(gardenId).isPresent()) {
                garden.getPlantList().add(newPlant);
                gardenRepository.save(garden);
            }
        }
        return newPlant;

    }

    @Override
    public void deleteGarden(long gardenId, String username) {
        Garden gardenToDelete = findGardenByIdAndUsername(gardenId, username);
        gardenRepository.delete(gardenToDelete);
    }

    @Override
    @Transactional
    public Garden updateGarden(long gardenId, GardenDto gardenDto, String currentUsername) {
        Garden garden = findGardenByIdAndUsername(gardenId, currentUsername);
        garden.setDescription(gardenDto.getDescription());
        garden.setName(gardenDto.getName());

        return gardenRepository.save(garden);
    }

    @Override
    @Transactional
    public void deletePlantFromGarden(long plantId, long gardenId) {
        if(gardenRepository.findById(gardenId).isPresent()){
            Garden garden = gardenRepository.findById(gardenId).get();
            if(plantRepository.findById(plantId).isPresent()){
                garden.getPlantList().remove(plantRepository.findById(plantId).get());
            }
        }
    }

    @Override
    public Garden findGardenByIdAndUsername(long gardenId, String username) {
        Garden garden = gardenRepository.findById(gardenId)
                .orElseThrow(() -> new RuntimeException("Garden not found with id: " + gardenId));
        if (!garden.getUser().getUsername().equals(username)) {
            throw new SecurityException("User does not have permission to access this garden.");
        }
        return garden;
    }

    @Override
    @Transactional
    public Garden createGardenForUser(GardenDto newGardenDto, String currentUsername){
        User user = userService.getUserByUsername(currentUsername);
        Garden newGarden = new Garden();
        List<Garden> gardenList = user.getGardenList();

        newGarden.setName(newGardenDto.getName());
        newGarden.setDescription(newGardenDto.getDescription());
        Garden savedGarden = gardenRepository.save(newGarden);
        if (gardenList != null) {
            gardenList.add(savedGarden);
            user.setGardenList(gardenList);
            userRepository.save(user);
        }
        return savedGarden;
    }

    @Override
    public List<Garden> findGardensByUsername(String currentUsername){
        User user = userService.getUserByUsername(currentUsername);
        return user.getGardenList();
    }

    @Override
    public Plant getPlantById(long plantId, long gardenId, String username) {
        Garden garden = findGardenByIdAndUsername(gardenId, username);
        Plant plant = new Plant();
        if(plantRepository.findById(plantId).isPresent()) {
            if (garden.getPlantList().contains(plantRepository.findById(plantId).get())){
                plant = plantRepository.findById(plantId).get();
            }
        }
        return plant;
    }

    @Override
    public List<PlantReportDTO> getPlantReport(String Username) {
        List<Garden> gardens = findGardensByUsername(Username);
        List<Plant> plants = new ArrayList<>();
        List<PlantReportDTO> report = new ArrayList<>();
        List<String> names = new ArrayList<>();
        names.add("");
        int i = 1;
        for (Garden garden : gardens) {
            plants.addAll(garden.getPlantList());
        }
        for (Plant plant : plants) {
            PlantReportDTO line = new PlantReportDTO();
            line.setId(i);
            i++;
            if(plant.getCommonName() != null) {
                line.setName(plant.getCommonName());
            } else if (plant.getSciName() != null) {
                line.setName(plant.getSciName());
            }
            line.setDescription(plant.getDescription());
            LocalDateTime established = plant.getCreated_on();

            double age = getAge(established);
            line.setYears_present(age);

            if(names.contains(plant.getCommonName()) || names.contains(plant.getSciName())) {
                int index = report.indexOf(line);
                int instances = report.get(index).getNum_instances();
                report.get(index).setNum_instances(instances+1);
                if (line.getYears_present() > report.get(index).getYears_present()){
                    report.get(index).setYears_present(line.getYears_present());
                }
                continue;
            }
            else {
                line.setNum_instances(1);
            }
            names.add(plant.getCommonName());
            names.add(plant.getSciName());
            report.add(line);
        }
        return report;
    }

    @Override
    public List<GardenReportDto> getGardenReport(String Username) {
        List<Garden> gardens = findGardensByUsername(Username);
        List<GardenReportDto> report = new ArrayList<>();
        int i = 1;
        for (Garden garden : gardens) {
            GardenReportDto line = new GardenReportDto();
            line.setId(i);
            i++;

            line.setGarden_name(garden.getName());
            line.setDescription(garden.getDescription());

            double age = getAge(garden.getCreated_on());
            line.setAge(age);

            line.setNum_plants(garden.getPlantList().size());
            report.add(line);
        }
        return report;
    }

    private double getAge(LocalDateTime date) {
        Period period = Period.between(LocalDate.from(date), LocalDate.now());
        int years = period.getYears();
        int months = period.getMonths();
        double fractionalMonths = months / 12.0;

        return fractionalMonths + years;

    }

}
