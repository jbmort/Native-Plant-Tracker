package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    //
    // New User Provided Garden Data Points
    //
    @Column(name = "location")
    private String location;

    @Column(name = "sunlight_level")
    private Integer sunlight;

    @Column(name = "soil_moisture")
    private Integer soilMoisture;

    @Column(name = "soil_type")
    private String soilType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "garden_goals",
            joinColumns = @JoinColumn(name = "garden_id"),
            inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private Set<Goal> goals = new HashSet<>();

    @Column(name = "area_sqft")
    private Double area;

    // Plants in the Garden
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSunlight() {
        return sunlight;
    }

    public void setSunlight(Integer sunlight) {
        this.sunlight = sunlight;
    }

    public Integer getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(Integer soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
