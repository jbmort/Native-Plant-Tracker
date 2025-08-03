package com.example.demo.Services;

import com.example.demo.DTO.GardenDto;
import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Entities.Garden;
import com.example.demo.Entities.User;
import com.example.demo.Repository.GardenRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GardenRepository gardenRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, GardenRepository gardenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.gardenRepository = gardenRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalStateException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalStateException("Error: Email is already in use!");
        }

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());

        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Garden> getGardensForUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get().getGardenList();
        }
        return null;
    }

    @Override
    public Garden addGardenForUser(GardenDto garden, long userID) {
        Optional<User> user = userRepository.findById(userID);

        if(user.isPresent()) {
            List<Garden> gardenList = user.get().getGardenList();

            Garden newGarden = new Garden();
            newGarden.setName(garden.getName());
            newGarden.setDescription(garden.getDescription());
            Garden savedGarden = gardenRepository.save(newGarden);
            if (gardenList != null) {
                gardenList.add(savedGarden);
            }
            return savedGarden;
        }
        return null;
    }


}
