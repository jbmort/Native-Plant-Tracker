package com.example.demo.service;


import com.example.demo.entities.GardenPlant;
import com.example.demo.repository.GardenRepository;
import com.example.demo.services.GardenService;
import com.example.demo.dto.GardenDto;
import com.example.demo.entities.Garden;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GardenServiceImplTest {

    @Mock
    private GardenRepository gardenRepository;

    @Mock
    private UserService userService;



    @InjectMocks
    private GardenService gardenService;

    // Test Data
    private User testUser;
    private GardenDto gardenDto;
    private Garden testGarden;

    @BeforeEach
    void setUp() {
        // Create test user object
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        // Create test DTO
        gardenDto = new GardenDto();
        gardenDto.setName("Test Garden");
        gardenDto.setDescription("A garden for testing.");

        // Create test Garden
        testGarden = new Garden();
        testGarden.setId(50L);
        testGarden.setName("Garden One");
        testGarden.setDescription("Basic Garden");
        testGarden.setUser(testUser);
        testGarden.setCreated_on(LocalDateTime.now());
        List<GardenPlant> plants = new ArrayList<>();
        testGarden.setGardenPlants(plants);
    };

    @Test
    void createGardenForUser_shouldCreateAndSaveGardenCorrectly() {
//        Set up actions for when the function is called
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);

        when(gardenRepository.save(any(Garden.class))).thenAnswer(invocation -> {
            Garden gardenToSave = invocation.getArgument(0);
            gardenToSave.setId(99L);
            return gardenToSave;
        });

//        Run Test
        Garden result = gardenService.createGardenForUser(gardenDto, "testuser");

//        Assertions on results to verify results
        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals("Test Garden", result.getName());
        assertEquals("A garden for testing.", result.getDescription());

        assertNotNull(result.getUser());
        assertEquals("testuser", result.getUser().getUsername());

//        Verify that the values of the created garden are as expected before being saved
        ArgumentCaptor<Garden> gardenArgumentCaptor = ArgumentCaptor.forClass(Garden.class);
        verify(gardenRepository, times(1)).save(gardenArgumentCaptor.capture());
        Garden capturedGarden = gardenArgumentCaptor.getValue();
        assertEquals("Test Garden", capturedGarden.getName());
        assertEquals(testUser, capturedGarden.getUser());
    }

    @Test
    void updateGarden_shouldCorrectlyUpdateGardenFields(){
            //      Set up actions for test function
            when(gardenRepository.findById(50L)).thenReturn(Optional.of(testGarden));
            when(gardenRepository.save(any(Garden.class))).thenAnswer(invocation ->
                    invocation.getArgument(0)
            );

            //        Run Test
            Garden result = gardenService.updateGarden(50L, gardenDto, "testuser");

            //        Make assertions
            assertNotNull(result);
            assertEquals(testGarden.getId(), result.getId());
            assertNotEquals("Garden One", result.getName());
            assertNotEquals("Basic Garden", result.getDescription());
            assertEquals(gardenDto.getName(), result.getName());
            assertEquals(gardenDto.getDescription(), result.getDescription());
            assertEquals(testUser.getUsername(), result.getUser().getUsername());
            assertEquals(testGarden.getCreated_on(), result.getCreated_on());
            assertEquals(testGarden.getGardenPlants().size(), result.getGardenPlants().size());
    }


}
