package com.example.demo.rest;

import com.example.demo.dto.ApiResultsDto;
import com.example.demo.entities.Plant;
import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.SecurityConfig;
import com.example.demo.services.ApiService;
import com.example.demo.services.GardenService;
import com.example.demo.services.PlantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiDataController.class)
@Import(SecurityConfig.class)
public class ApiDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @MockitoBean
    private PlantService plantService;

    @MockitoBean
    private ApiDataController apiDataController;

    @MockitoBean
    private Authentication authentication;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private ApiService apiService;


    @BeforeEach
    public void setup() {
//        ApiResultsDto apiResultsDto = new ApiResultsDto();
//        apiResultsDto.setCommonName("Common Milkweed");
//        apiResultsDto.setScientificName("Asclepias syriaca");
//        apiResultsDto.setImageUrl("www.example.com");
//        apiResultsDto.setExternalId(100L);
    }

    @Test
    void shouldReturnPlantSearchData() throws Exception {
//        when(apiDataController.searchPlantsApiByName("milkweed", authentication))
//                .thenReturn();
        List<ApiResultsDto> expectedContent = new ArrayList<>();
        ApiResultsDto apiResultsDto = new ApiResultsDto();
        apiResultsDto.setCommonName("Common Milkweed");
        apiResultsDto.setScientificName("Asclepias syriaca");
        apiResultsDto.setImageUrl("www.example.com");
        apiResultsDto.setExternalId(100L);
        expectedContent.add(apiResultsDto);


        when(authentication.getName()).thenReturn("admin");
        when(plantService.getPlantsByName("milkweed")).thenReturn(Collections.emptyList());
        when(apiService.searchPlantsApi("milkweed")).thenReturn(expectedContent);


        this.mockMvc.perform(get("/api/search/milkweed", authentication).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
