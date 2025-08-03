package com.example.demo.Repository;

import com.example.demo.Entities.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    public Boolean existsByName(String name);
    public Garden findByName(String name);
}
