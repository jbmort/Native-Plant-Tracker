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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiDataController.class)
@Import(SecurityConfig.class)
@WithMockUser("admin")
public class ApiDataControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlantService plantService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private ApiService apiService;


    List<ApiResultsDto> expectedContent = new ArrayList<>();

    List<Plant> databaseContent = new ArrayList<>();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ApiResultsDto apiResultsDto = new ApiResultsDto();
        apiResultsDto.setCommonName("Common Milkweed");
        apiResultsDto.setScientificName("Asclepias syriaca");
        apiResultsDto.setImageUrl("www.example.com");
        apiResultsDto.setExternalId(100L);
        expectedContent.add(apiResultsDto);

        Plant apiResultsDto2 = new Plant();
        apiResultsDto2.setCommonName("Ironweed");
        apiResultsDto2.setId(200L);
        Plant apiResultsDto3 = new Plant();
        apiResultsDto3.setCommonName("Goldenrod");
        apiResultsDto3.setId(300L);
        Plant apiResultsDto4 = new Plant();
        apiResultsDto4.setCommonName("Sky Blue Aster");
        apiResultsDto4.setId(400L);

        databaseContent.add(apiResultsDto2);
        databaseContent.add(apiResultsDto3);
        databaseContent.add(apiResultsDto4);

    }

    @Test
    void shouldReturnPlantSearchData_whenDbIsEmpty() throws Exception {
        String searchTerm = "milkweed";

        when(plantService.getPlantsByName(searchTerm)).thenReturn(Collections.emptyList());
        when(apiService.searchPlantsApi(searchTerm)).thenReturn(expectedContent);


        this.mockMvc.perform(get("/api/search/{searchName}", searchTerm).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedContent)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].commonName", hasToString("Common Milkweed")))
        ;
    }

    @Test
    void shouldReturnPlantSearchData_whenDbIsNotEmpty() throws Exception {
        String searchTerm = "e";
        when(plantService.getPlantsByName(searchTerm)).thenReturn(databaseContent);
        when(apiService.searchPlantsApi(searchTerm)).thenReturn(expectedContent);

        this.mockMvc.perform(get("/api/search/{searchName}", searchTerm).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

    }
}
