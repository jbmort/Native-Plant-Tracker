package com.example.demo.repository;

import com.example.demo.entities.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    Boolean existsByName(String name);
    Garden findByName(String name);
}
