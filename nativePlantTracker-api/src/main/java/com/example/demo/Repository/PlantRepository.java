package com.example.demo.Repository;

import com.example.demo.Entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    public Plant getPlantById(long id);
    public Plant getPlantByCommonName(String name);
    public Boolean existsPlantByCommonName(String name);

}
