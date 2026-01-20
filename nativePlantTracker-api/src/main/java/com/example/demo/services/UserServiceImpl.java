package com.example.demo.services;

import com.example.demo.dto.GardenDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entities.Garden;
import com.example.demo.entities.User;
import com.example.demo.repository.GardenRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
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
        return user.map(User::getGardenList).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Garden> getGardensForUsername(String username) {
        User user = findByUsername(username); // Use the secure method
        return user.getGardenList();
    }

    @Transactional
    @Override
    public Garden addGardenForUser(GardenDto gardenDto, String username) {
        User user = findByUsername(username);

        Garden newGarden = new Garden();
        newGarden.setName(gardenDto.name());
        newGarden.setDescription(gardenDto.description());

        newGarden.setUser(user);

        return gardenRepository.save(newGarden);
    }


}
