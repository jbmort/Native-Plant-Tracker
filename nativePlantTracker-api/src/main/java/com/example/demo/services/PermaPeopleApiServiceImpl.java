package com.example.demo.services;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.ApiResultsDto;
import com.example.demo.dto.apiResponsePlantDto;
import com.example.demo.entities.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermaPeopleApiServiceImpl implements PermaPeopleApiService {

    @Value("${api.service.perm-token}")
    private String token;
    @Value("${api.service.perm-id}")
    private String apiId;
//    private String baseUrl = "https://trefle.io/api/v1/plants";
    private String baseUrl = "https://permapeople.org/api/";
    RestTemplate restTemplate;


    @Override
    public List<ApiResultsDto> searchPlants(String plantName) {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/search")
                .queryParam("q", plantName)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-permapeople-key-id", apiId);
        headers.set("x-permapeople-key-secret", token);

        // For a GET request, the body is null.
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        List<apiResponsePlantDto> plantList;

        try {
            ResponseEntity<ApiResponseDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST, // Specify the HTTP method
                    entity,         // Pass the entity with the headers
                    ApiResponseDto.class // The expected response type
            );
            ApiResponseDto response = responseEntity.getBody();

            if (response != null && response.getPlants() != null) {
                plantList = new ArrayList<>(response.getPlants());
                return plantDtoConverter(plantList);
            }
        }
        catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Plant getPlantData(long id) {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/plants/" + id)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-permapeople-key-id", apiId);
        headers.set("x-permapeople-key-secret", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<apiResponsePlantDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET, // Specify the HTTP method
                    entity,         // Pass the entity with the headers
                    apiResponsePlantDto.class // The expected response type
            );
            apiResponsePlantDto response = responseEntity.getBody();
            if (response != null && response.scientificName() != null) {
                return ApiResponseToPlantConverter(response);
            }
        }
        catch (Exception e){
            System.err.println("Error calling API: " + e.getMessage());
    }
    return null;
    }

    private List<ApiResultsDto> plantDtoConverter(List<apiResponsePlantDto> plantList){
        List<ApiResultsDto> resultList = new ArrayList<>();
        plantList.forEach(plantDto -> {
            ApiResultsDto resultDto = new ApiResultsDto();
            resultDto.setCommonName(plantDto.name());
            resultDto.setExternalId(plantDto.id());
            resultDto.setScientificName(plantDto.scientificName());
            resultDto.setImageUrl(plantDto.images().thumb());

            resultList.add(resultDto);
        });
        return resultList;
    }

    private Plant ApiResponseToPlantConverter(apiResponsePlantDto apiResponsePlantDto){
        Plant plant = new Plant();
        plant.setId(apiResponsePlantDto.id());
        plant.setCommonName(apiResponsePlantDto.name());
        plant.setSciName(apiResponsePlantDto.scientificName());
        plant.setImageUrl(apiResponsePlantDto.images().thumb());
        plant.setDescription(apiResponsePlantDto.description());

        // Create Switch case to inside loop to handle grabbing all data points such as moisture requirements and edibility
        return plant;
    }

}
