package com.example.demo.services;

import com.example.demo.dto.ApiResultsDto;
import com.example.demo.entities.Plant;

import java.util.List;

public interface PermaPeopleApiService {
    List<ApiResultsDto> searchPlants(String plantName);
    Plant getPlantData(long id);
}
