package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="Gardens")
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    private LocalDateTime created_on;

    @OneToMany(
            mappedBy = "garden",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference("garden-gardenplant")
    private List<GardenPlant> gardenPlants = new ArrayList<>();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Defines the FK column in THIS table.
    @JsonBackReference("user-garden")
    private User user;



    public Garden(String name, @Nullable String description) {
        this.name = name;
        this.description = description;
        this.created_on = LocalDateTime.now();
    }

    public Garden() {
        this.name = null;
        this.description = null;
        this.created_on = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDateTime created_on) {
        this.created_on = created_on;
    }

    public List<GardenPlant> getGardenPlants() {
        return gardenPlants;
    }

    public void setGardenPlants(List<GardenPlant> gardenPlants) {
        this.gardenPlants = gardenPlants;
    }

    public void removeGardenPlant(GardenPlant gardenPlant) {
        gardenPlants.remove(gardenPlant);
        gardenPlant.setGarden(null); // Remove the back-reference
    }
}
