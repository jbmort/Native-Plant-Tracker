package com.example.demo.services;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.ApiResultsDto;
import com.example.demo.dto.apiResponsePlantDto;
import com.example.demo.entities.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl  implements ApiService {
    private final RestTemplate restTemplate;

    @Autowired
    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


// API values //
    @Value("${permapeople.api.baseUrl}")
    private String baseUrl;

    @Value("${permapeople.api.key}")
    private String apiKey;

    @Value("${permapeople.api.secret}")
    private String apiSecret;


    // Search for plants that match name //

    @Override
    public List<ApiResultsDto> searchPlantsApi(String name) {
        String url = UriComponentsBuilder.fromUriString(baseUrl + "search")
                .queryParam("q", name)
                .toUriString();

        // Add authentication header
        HttpEntity<Void> requestEntity = headers();

        // Send API call
        try {
            ResponseEntity<ApiResponseDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    ApiResponseDto.class
            );

            // Collect API Response
            ApiResponseDto response = responseEntity.getBody();

            if (response != null && response.getPlants() != null) {
                return response.getPlants().stream()
                        .filter(plant -> plant.scientificName() != null && !plant.scientificName().isEmpty())
                        .map(this::toApiResultsDto) // Translate response into simple version to list on front end
                        .collect(Collectors.toList());
            }
        }
        catch (Exception e){
            System.err.println("Error calling Permapeople API: " + e.getMessage());
        }
        return List.of();
    }

    // Select a single plant from API //
    @Override
    public apiResponsePlantDto getPlant(long id) {
        //create get request with "plant/<id>"
        return null;}


    // //   Helper Methods   // //

    private  HttpEntity<Void> headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-permapeople-key-id", apiKey);
        headers.set("x-permapeople-key-secret", apiSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }

    private ApiResultsDto toApiResultsDto(apiResponsePlantDto Plant) {
        ApiResultsDto dto = new ApiResultsDto();
        dto.setExternalId(Plant.id());
        dto.setCommonName(Plant.name());
        dto.setScientificName(Plant.scientificName());
        if (Plant.images() != null) {
            dto.setImageUrl(Plant.images().thumb());
        }
        return dto;
    }

}
