package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entities.Garden;
import com.example.demo.entities.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    User getUser(long id);
    User getUserByUsername(String username);
    User registerUser(UserRegistrationDto registrationDto);
    void delete(long id);
    List<Garden> getGardensForUser(long id);

    User findByUsername(String username);

    List<Garden> getGardensForUsername(String username);
    @Transactional
    Garden addGardenForUser(GardenDto gardenDto, String username);
}
