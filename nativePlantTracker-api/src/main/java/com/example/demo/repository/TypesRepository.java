package com.example.demo.repository;

import com.example.demo.entities.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypesRepository extends JpaRepository<PlantType, Long> {
}
