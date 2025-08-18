package com.example.demo.rest;

import com.example.demo.dto.*;
import com.example.demo.entities.Garden;
import com.example.demo.entities.Plant;
import com.example.demo.entities.PlantType;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/gardens")
public class GardenController {

    final ApplicationContext context;
    final GardenService gardenService;
    final PlantService plantService;
    private final UserService userService;

    @Autowired
    public GardenController(ApplicationContext context,
                            GardenService gardenService,
                            PlantService plantService, UserService userService) {
        this.context = context;
        this.gardenService = gardenService;
        this.plantService = plantService;
        this.userService = userService;
    }

    // /////////////// //
    // GARDEN REQUESTS //
    // /////////////// //

    // 1. GET all gardens for the LOGGED-IN user
    @GetMapping
    public ResponseEntity<List<Garden>> getGardensForCurrentUser(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<Garden> gardens = gardenService.findGardensByUsername(currentUsername);
        System.out.println(gardens);
        return ResponseEntity.ok(gardens);
    }

    // 2. GET a single garden, ensuring it belongs to the logged-in user
    @GetMapping("/{gardenId}")
    public ResponseEntity<Garden> getGardenById(@PathVariable long gardenId,
                                                Authentication authentication) {
        String currentUsername = authentication.getName();
        Garden garden = gardenService.findGardenByIdAndUsername(gardenId, currentUsername);
        return ResponseEntity.ok(garden);
    }

    // 3. POST a new garden for the logged-in user
    @PostMapping
    public ResponseEntity<Garden> addGarden(@RequestBody GardenDto newGardenDto,
                                            Authentication authentication) {
        String currentUsername = authentication.getName();
        Garden createdGarden = userService.addGardenForUser(newGardenDto, currentUsername);
        ResponseEntity<Garden> gardenResponseEntity;
        gardenResponseEntity = new ResponseEntity<>(createdGarden, HttpStatus.CREATED);
        return gardenResponseEntity;
    }

    // 4. PUT (update) an existing garden
    @PutMapping("/{gardenId}")
    public ResponseEntity<Garden> updateGarden(@PathVariable long gardenId,
                                               @RequestBody GardenDto gardenDto,
                                               Authentication authentication) {
        String currentUsername = authentication.getName();
        Garden updatedGarden = gardenService.updateGarden(gardenId, gardenDto, currentUsername);
        return ResponseEntity.ok(updatedGarden);
    }

    // 5. DELETE a garden
    @DeleteMapping("/{gardenId}")
    public ResponseEntity<Void> deleteGarden(@PathVariable long gardenId,
                                             Authentication authentication) {
        String currentUsername = authentication.getName();
        gardenService.deleteGarden(gardenId, currentUsername);
        return ResponseEntity.noContent().build();
    }

    // ///////////////////////// //
    // GARDEN PLANT SUB-RESOURCE //
    // ///////////////////////// //

    // 6. GET plants for a specific garden
    @GetMapping("/{gardenId}/plants")
    public ResponseEntity<List<PlantDto>> getGardenPlants(@PathVariable long gardenId,
                                                       Authentication authentication) {
        String currentUsername = authentication.getName();
        List<PlantDto> plants = gardenService.getAllPlantsForGarden(gardenId, currentUsername);
        return ResponseEntity.ok(plants);
    }

    // 7. POST (add) a new plant to a garden
    @PostMapping("/{gardenID}/plants")
    public ResponseEntity<Plant> addPlantToGarden(@PathVariable long gardenID,
                                                  @RequestBody PlantDto plant,
                                                  Authentication authentication) {
        String currentUsername = authentication.getName();
        Plant newPlant = gardenService.addPlantToGarden(plant, gardenID, currentUsername);
        if (newPlant != null) {
            return ResponseEntity.ok(newPlant);
        }
        return ResponseEntity.noContent().build();
    }

    // 8. PUT (update) a plant for a specific garden
    @PutMapping("/{gardenID}/{plantID}")
    public ResponseEntity<Plant> updatePlant(@PathVariable long gardenID,
                                             @PathVariable long plantID,
                                             @RequestBody PlantDto plantDTO,
                                             Authentication authentication) {
        String currentUsername = authentication.getName();
        Plant plant = gardenService.getPlantById(gardenID, plantID, currentUsername);
        Plant updatedPlant = plantService.updatePlant(plant.getId(), plantDTO);
        return ResponseEntity.ok(updatedPlant);
    }

    // 9. DELETE a plant from a garden
    @DeleteMapping("/{gardenID}/{plantID}")
    public ResponseEntity<Void> deletePlant(@PathVariable long gardenID,
                                             @PathVariable long plantID,
                                             Authentication authentication) {
        String currentUsername = authentication.getName();
        Plant plant = gardenService.getPlantById(gardenID, plantID, currentUsername);
        gardenService.deletePlantFromGarden(gardenID, plant.getId());
        return ResponseEntity.noContent().build();
    }

    // 10. GET all plants for the user
    @GetMapping("/plants")
    public ResponseEntity<List<Plant>> getGardenPlants(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<Plant> plants = gardenService.allPlantsForUser(currentUsername);
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/types")
    public ResponseEntity<List<PlantType>> getPlantTypes(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<PlantType> types = new ArrayList<>();
        if(currentUsername != null) {
            types = plantService.getTypes();
        }
        return ResponseEntity.ok(types);
    }

    @PostMapping("/types")
    public ResponseEntity<PlantType> addPlantType(@RequestBody PlantTypeDto plantTypeDto, Authentication authentication) {
        String currentUsername = authentication.getName();
        PlantType plantType = new PlantType();
        if(currentUsername != null) {
            plantType = plantService.addPlantType(plantTypeDto);
        }
        return ResponseEntity.ok(plantType);
    }



//    // 12. Test POST
//    @PostMapping("/test")
//    public ResponseEntity<String> testGarden(@RequestBody GardenDto gardenDto) {
//        gardenService.addGarden(gardenDto);
//        return ResponseEntity.ok("success");
//    }
//
//    // 13. test GET
//    @GetMapping("/test")
//    public ResponseEntity<List<Garden>> testGardens() {
//        List<Garden> gardens = gardenService.getGardens();
//        return ResponseEntity.ok(gardens);
//    }
}



