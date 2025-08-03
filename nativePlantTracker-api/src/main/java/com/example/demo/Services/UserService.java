package com.example.demo.Services;

import com.example.demo.DTO.GardenDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Entities.Garden;
import com.example.demo.Entities.User;

import java.util.List;

public interface UserService {
    public User getUser(long id);
    public User getUserByUsername(String username);
    public User registerUser(UserRegistrationDto registrationDto);
    public void delete(long id);
    public List<Garden> getGardensForUser(long id);
    public Garden addGardenForUser(GardenDto garden, long userID);
}
