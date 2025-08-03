package com.example.demo.entities;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
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

    @ManyToMany
    @JoinTable(name="Garden_Plants",
            joinColumns=
            @JoinColumn(name="Garden_ID", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="Plant_ID", referencedColumnName="id")
    )
    private List<Plant> plantList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="garden_id")
    private User user;

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }



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
}
