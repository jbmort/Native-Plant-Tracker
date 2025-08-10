package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.GardenReportDto;
import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantReportDTO;
import com.example.demo.entities.Garden;
import com.example.demo.entities.GardenPlant;
import com.example.demo.entities.Plant;
import com.example.demo.entities.User;
import com.example.demo.repository.GardenPlantRepository;
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
import java.util.stream.IntStream;

@Service
public class GardenServiceImpl implements GardenService {

    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final UserService userService;
    private final GardenPlantRepository gardenPlantRepository;
    private final UserRepository userRepository;


    @Autowired
    GardenServiceImpl(GardenRepository gardenRepository, PlantRepository plantRepository, UserService userService, GardenPlantRepository gardenPlantRepository, UserRepository userRepository) {
        this.gardenRepository = gardenRepository;
        this.plantRepository = plantRepository;
        this.userService = userService;
        this.gardenPlantRepository = gardenPlantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Garden> getGardens() {
        return gardenRepository.findAll();
    }

    @Override
    public List<Plant> getAllPlantsForGarden(long gardenId, String currentUsername) {
        Garden garden = findGardenByIdAndUsername(gardenId, currentUsername);
        List<Plant> plantList = new ArrayList<>();
        List<GardenPlant> list = gardenPlantRepository.findByGardenId(garden.getId());
        for (GardenPlant gardenPlant : list) {
            plantList.add(gardenPlant.getPlant());
        }

        return plantList;
    }

    @Override
    public List<Plant> allPlantsForUser(String username){
        List<Garden> gardens = findGardensByUsername(username);
        List<Plant> plantList = new ArrayList<>();
        for (Garden garden : gardens) {
            List<GardenPlant> reflist = gardenPlantRepository.findByGardenId(garden.getId());
            for (GardenPlant gardenPlant : reflist) {
                plantList.add(gardenPlant.getPlant());
            }
        }
        return plantList;
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
        boolean exists = false;
        for(GardenPlant gardenPlant : garden.getGardenPlants()){
            if (gardenPlant.getPlant().getCommonName().equals(plant.getCommon_name())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            if (garden.getUser().getUsername().equals(currentUsername)) {
                if (!plantRepository.existsPlantByCommonName(plant.getCommon_name())) {
                    newPlant.setCommonName(plant.getCommon_name());
                    newPlant.setDescription(plant.getDescription());
                    newPlant.setSciName(plant.getSci_name());
                    newPlant.setCreated_on(LocalDateTime.now());
                    plantRepository.save(newPlant);
                } else {
                    newPlant = plantRepository.getPlantByCommonName(plant.getCommon_name());
                    if (newPlant == null) {
                        newPlant = plantRepository.getPlantBySciName(plant.getSci_name());
                    }
                }

                if (gardenRepository.findById(gardenId).isPresent()) {
                    GardenPlant gardenPlant = new GardenPlant();
                    gardenPlant.setGarden(garden);
                    gardenPlant.setPlant(newPlant);
                    garden.getGardenPlants().add(gardenPlant);
                    gardenRepository.save(garden);
                }
            }
            return newPlant;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteGarden(long gardenId, String username) {
        Garden garden = findGardenByIdAndUsername(gardenId, username);
        if(gardenRepository.findByIdWithPlants(garden.getId()).isPresent()) {
            Garden gardenToDelete = gardenRepository.findByIdWithPlants(garden.getId()).get();
            gardenRepository.delete(gardenToDelete);
        }
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
    public void deletePlantFromGarden(long gardenId, long plantId) {
        if(gardenRepository.findById(gardenId).isPresent()){
            Garden garden = gardenRepository.findById(gardenId).get();
            if(plantRepository.findById(plantId).isPresent()){
                List<GardenPlant> list = garden.getGardenPlants();
                list.removeIf(gardenPlant -> gardenPlant.getPlant().getId() == plantId);
                garden.setGardenPlants(list);
                gardenRepository.save(garden);
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
//        List<Garden> gardenList = user.getGardenList();

        newGarden.setName(newGardenDto.getName());
        newGarden.setDescription(newGardenDto.getDescription());
        newGarden.setUser(user);
        Garden savedGarden = gardenRepository.save(newGarden);
//        if (gardenList != null) {
//            gardenList.add(savedGarden);
//            user.setGardenList(gardenList);
//            userRepository.save(user);
//        }
        return savedGarden;
    }

    @Override
    public List<Garden> findGardensByUsername(String currentUsername){
        User user = userService.getUserByUsername(currentUsername);
        return user.getGardenList();
    }

    @Override
    public Plant getPlantById(long gardenId, long plantId, String username) {
        Garden garden = findGardenByIdAndUsername(gardenId, username);
        if(plantRepository.findById(plantId).isPresent()) {
            Optional <GardenPlant> plantReference = gardenPlantRepository.findFirstByGardenIdAndPlantId(garden.getId(), plantId);
            if(plantReference.isPresent()) {
            GardenPlant gardenPlant = plantReference.get();
            return gardenPlant.getPlant();}
        }
        return null;
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
            List<GardenPlant> refList = gardenPlantRepository.findByGardenId(garden.getId());
            for(GardenPlant ref : refList){
                plants.add(ref.getPlant());
            }
        }
        for (Plant plant : plants) {
            PlantReportDTO line = new PlantReportDTO();
            line.setId(i);
            i++;
            if (plant.getCommonName() != null) {
                line.setName(plant.getCommonName());
            } else if (plant.getSciName() != null) {
                line.setName(plant.getSciName());
            }
            line.setDescription(plant.getDescription());
            LocalDateTime established = plant.getCreated_on();

//            double age = getAge(established);
            line.setYears_present(established.getYear());

            line.setNum_instances(1);

            long count = plants.stream()
                    .filter(p -> line.getName().equalsIgnoreCase(p.getCommonName()))
                    .count();
            line.setNum_instances((int) count);

            if(!names.contains(plant.getCommonName().toLowerCase())) {

            names.add(plant.getCommonName().toLowerCase());
//            names.add(plant.getSciName().toLowerCase());
            report.add(line);}
        }
        System.out.println(report);
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
            line.setAge(garden.getCreated_on().getYear());

            line.setNum_plants(garden.getGardenPlants().size());
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
