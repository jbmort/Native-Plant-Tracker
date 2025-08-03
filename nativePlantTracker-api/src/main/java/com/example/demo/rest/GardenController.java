package com.example.demo.rest;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.GardenReportDto;
import com.example.demo.dto.PlantDto;
import com.example.demo.dto.PlantReportDTO;
import com.example.demo.entities.Garden;
import com.example.demo.entities.Plant;
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
@CrossOrigin(origins="*")
//@CrossOrigin("http://localhost:4200")
public class GardenController {

    final ApplicationContext context;
    final GardenService gardenService;
    final PlantService plantService;

    @Autowired
    public GardenController(ApplicationContext context,
                            GardenService gardenService,
                            PlantService plantService) {
        this.context = context;
        this.gardenService = gardenService;
        this.plantService = plantService;
    }

    // /////////////// //
    // GARDEN REQUESTS //
    // /////////////// //

    // 1. GET all gardens for the LOGGED-IN user
    @GetMapping
    public ResponseEntity<List<Garden>> getGardensForCurrentUser(Authentication authentication) {
        // The 'authentication' object is provided by Spring Security. It's trustworthy.
        String currentUsername = authentication.getName();
        List<Garden> gardens = gardenService.findGardensByUsername(currentUsername);
        return ResponseEntity.ok(gardens);
    }

    // 2. GET a single garden, ensuring it belongs to the logged-in user
    @GetMapping("/{gardenId}")
    public ResponseEntity<Garden> getGardenById(@PathVariable long gardenId,
                                                Authentication authentication) {
        String currentUsername = authentication.getName();
        // The service layer will be responsible for checking ownership
        Garden garden = gardenService.findGardenByIdAndUsername(gardenId, currentUsername);
        return ResponseEntity.ok(garden);
    }

    // 3. POST a new garden for the logged-in user
    @PostMapping
    public ResponseEntity<Garden> addGarden(@RequestBody GardenDto newGardenDto,
                                            Authentication authentication) {
        String currentUsername = authentication.getName();
        Garden createdGarden = gardenService.createGardenForUser(newGardenDto, currentUsername);
        return new ResponseEntity<>(createdGarden, HttpStatus.CREATED);
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
    public ResponseEntity<List<Plant>> getGardenPlants(@PathVariable long gardenId,
                                                       Authentication authentication) {
        String currentUsername = authentication.getName();
        List<Plant> plants = gardenService.getAllPlantsForGarden(gardenId, currentUsername);
        return ResponseEntity.ok(plants);
    }

    // 7. POST (add) a new plant to a garden
    @PostMapping("/{gardenID}/plants")
    public ResponseEntity<Plant> addPlantToGarden(@PathVariable long gardenID,
                                                  @RequestBody PlantDto plant,
                                                  Authentication authentication) {
        String currentUsername = authentication.getName();
        Plant newPlant = gardenService.addPlantToGarden(plant, gardenID, currentUsername);
        return ResponseEntity.ok(newPlant);
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
        plantService.deletePlant(plant.getId());
        return ResponseEntity.noContent().build();
    }

    // 9. GET all plants for the user
    @GetMapping("/plants")
    public ResponseEntity<List<Plant>> getGardenPlants(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<Garden> gardens = gardenService.findGardensByUsername(currentUsername);
        List<Plant> plants = new ArrayList<>();
        for (Garden garden : gardens) {
            plants.addAll(garden.getPlantList());
        }
        return ResponseEntity.ok(plants);
    }

    // 10. GET a generated plant report object for the user
    @GetMapping("plants/report")
    public ResponseEntity<List<PlantReportDTO>> getPlantReport(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<PlantReportDTO> report = gardenService.getPlantReport(currentUsername);
        return ResponseEntity.ok(report);
    }

    // 11. GET a generated garden report object for the user
    @GetMapping("/report")
    public ResponseEntity<List<GardenReportDto>> getGardenReport(Authentication authentication) {
        String currentUsername = authentication.getName();
        List<GardenReportDto> report = gardenService.getGardenReport(currentUsername);
        return ResponseEntity.ok(report);
    }

    // 12. Test POST
    @PostMapping("/test")
    public ResponseEntity<String> testGarden(@RequestBody GardenDto gardenDto) {
        gardenService.addGarden(gardenDto);
        return ResponseEntity.ok("success");
    }

    // 13. test GET
    @GetMapping("/test")
    public ResponseEntity<List<Garden>> testGardens() {
        List<Garden> gardens = gardenService.getGardens();
        return ResponseEntity.ok(gardens);
    }
}



