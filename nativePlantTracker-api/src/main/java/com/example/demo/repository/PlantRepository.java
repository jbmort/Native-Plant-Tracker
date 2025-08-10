package com.example.demo.repository;

import com.example.demo.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant getPlantById(long id);
    Plant getPlantByCommonName(String name);
    Boolean existsPlantByCommonName(String name);

    Plant getPlantBySciName(String name);

    Boolean existsPlantBySciName(String name);
}
