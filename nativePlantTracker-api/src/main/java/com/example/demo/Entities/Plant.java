package com.example.demo.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String commonName;

    @Nullable
    private String sciName;

    @Nullable
    private String description;

    @NotNull
    private LocalDateTime created_on;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "plantList")
    private List<Garden> gardenList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String common_name) {
        this.commonName = common_name;
    }

    @Nullable
    public String getSciName() {
        return sciName;
    }

    public void setSciName(@Nullable String sci_name) {
        this.sciName = sci_name;
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

    public List<Garden> getGardenList() {
        return gardenList;
    }

    public void setGardenList(List<Garden> gardenList) {
        this.gardenList = gardenList;
    }
}
