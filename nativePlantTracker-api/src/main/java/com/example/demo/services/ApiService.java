package com.example.demo.services;

import com.example.demo.dto.ApiResultsDto;
import com.example.demo.dto.apiResponsePlantDto;
import com.example.demo.entities.Plant;

import java.util.List;

public interface ApiService {

    List<ApiResultsDto> searchPlantsApi(String name);

    public Plant getPlant(long id);
}
