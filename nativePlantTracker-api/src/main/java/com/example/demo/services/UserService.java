package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entities.Garden;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    User getUser(long id);
    User getUserByUsername(String username);
    User registerUser(UserRegistrationDto registrationDto);
    void delete(long id);
    List<Garden> getGardensForUser(long id);
    Garden addGardenForUser(GardenDto garden, long userID);
}
