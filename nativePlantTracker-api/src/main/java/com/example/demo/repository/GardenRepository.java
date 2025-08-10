package com.example.demo.repository;

import com.example.demo.entities.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    Boolean existsByName(String name);
    Garden findByName(String name);

    @Query("SELECT g FROM Garden g LEFT JOIN FETCH g.gardenPlants WHERE g.id = :id")
    Optional<Garden> findByIdWithPlants(@Param("id") Long id);
}

