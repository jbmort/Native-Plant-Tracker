package com.example.demo.entities;

import com.example.demo.dto.PlantTypeDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plant_types")
public class PlantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "value")
    private String value;

    @OneToMany(
            mappedBy = "plantType",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Plant> plants = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public PlantType() {}


    public PlantType(PlantTypeDto plantTypeDto) {
        this.setName(plantTypeDto.getName());
        this.setValue(plantTypeDto.getValue());
    }



}
