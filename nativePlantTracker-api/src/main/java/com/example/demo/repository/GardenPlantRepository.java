package com.example.demo.repository;


import com.example.demo.entities.GardenPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GardenPlantRepository extends JpaRepository<GardenPlant, Long> {

    List<GardenPlant> findByGardenId(Long gardenId);

    Optional<GardenPlant> findFirstByGardenIdAndPlantIdAndDateAbsentIsNull(long gardenId, long plantId, LocalDate dateAbsent);
    Optional<GardenPlant> findByIdAndGardenId(Long gardenPlantId, Long gardenId);

    @Query("SELECT COUNT(gp.plant) " +
            "FROM GardenPlant gp " +
            "WHERE gp.plant.id = :plantId AND gp.garden.user.id = :userId")
    Long countOccurrencesByPlantIdAndUserId(@Param("plantId") Long plantId, @Param("userId") Long userId);

    List<GardenPlant> getGardenPlantByGarden_IdAndDateAbsentIsNull(long gardenId, LocalDate dateAbsent);
}