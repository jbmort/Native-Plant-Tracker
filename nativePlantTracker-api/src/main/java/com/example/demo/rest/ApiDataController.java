package com.example.demo.rest;

import com.example.demo.dto.ApiResultsDto;
import com.example.demo.entities.Plant;
import com.example.demo.services.ApiService;
import com.example.demo.services.PlantService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/search")
public class ApiDataController{
    final ApplicationContext context;
    final ApiService apiService;
    private final PlantService plantService;

    public ApiDataController(ApplicationContext context, ApiService apiService, PlantService plantService) {
        this.context = context;
        this.apiService = apiService;
        this.plantService = plantService;
    }

    @GetMapping
    public ResponseEntity<List<ApiResultsDto>> searchPlantsApiByName(
            @RequestParam("q") String searchName
    ) {
        System.out.println("call for plant search received");
        // Check database for search term
        List<Plant> plantList = plantService.findPlantsByName(searchName);

        // Totally new plant with no similarity in database
        if(plantList.isEmpty()) {
            System.out.println("plantList is empty");
            List<ApiResultsDto> response = new ArrayList<>();
            List<ApiResultsDto> searchResults = apiService.searchPlantsApi(searchName);
            if(searchResults.size() <= 10) {
                response =  searchResults;
            }
            else {
                response = searchResults.subList(0, 10);
            }
            return ResponseEntity.ok(response);
        }

        // Few responses from database and search finished with API
        if(plantList.size() <= 10) {
            Set<ApiResultsDto> responseSet = new HashSet<>();

            plantList.forEach(plant -> {
                ApiResultsDto convertedPlant = convertToApiResultDto(plant);
                responseSet.add(convertedPlant);
            });

            List<ApiResultsDto> apiPlants = apiService.searchPlantsApi(searchName);
            responseSet.addAll(apiPlants);
            List<ApiResultsDto> response = responseSet.stream().toList();
            if(response.size() <= 10) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(response.subList(0, 10));
        }

        // Search populated completely from database
        List<ApiResultsDto> response = new ArrayList<>();
        plantList.subList(0,10).forEach(plant->{
            ApiResultsDto plantResult = convertToApiResultDto(plant);
            response.add(plantResult);
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plant/{id}")
    public ResponseEntity<String> searchPlantById(
            @PathVariable String id) {

        // Check database for plant by id
        Plant databasePlant = plantService.getPlant(Long.parseLong(id));
        if(databasePlant == null) {
            // Pull full plant data from api and save plant to database for future reference
            Plant apiPlant = apiService.getPlant(Long.parseLong(id));
            if(apiPlant == null) {
                plantService.addPlant(apiPlant);
                return ResponseEntity.ok("Plant added to database");
            }
            else{ return ResponseEntity.internalServerError().build();}
        }
        return ResponseEntity.ok("Plant already exists in database");
    }


    private ApiResultsDto convertToApiResultDto(Plant plant) {
        ApiResultsDto response = new ApiResultsDto();

        response.setExternalId(plant.getId());
        response.setCommonName(plant.getCommonName());
        response.setScientificName(plant.getSciName());
        response.setImageUrl(plant.getImageUrl());
        return  response;
    }
}
