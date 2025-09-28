package com.example.demo.entities; // Or your 'entities' package

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "garden_plants")
public class GardenPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_id", nullable = false)
    @JsonBackReference("garden-gardenplant")
    private Garden garden;

    // --- Relationship to Plant (The "Many" side) ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_id", nullable = false) // Foreign key column in this table
    private Plant plant;

    // --- Additional Columns for this Relationship ---

//    @NotNull
//    @Column(name = "quantity")
//    private Integer quantity;
//
    @Nullable
    @Column(name = "date_planted")
    private LocalDate datePlanted;

    @Nullable
    public LocalDate getDateAbsent() {
        return dateAbsent;
    }

    public void setDateAbsent(@Nullable LocalDate dateAbsent) {
        this.dateAbsent = dateAbsent;
    }

    @Nullable
    public LocalDate getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(@Nullable LocalDate datePlanted) {
        this.datePlanted = datePlanted;
    }

    @Nullable
    @Column(name = "date_absent")
    private LocalDate dateAbsent;
//
//    @Lob // Use @Lob for potentially long text fields
//    @Column(name = "notes")
//    private String notes;

    // --- Constructors, Getters, and Setters ---

    public GardenPlant() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public LocalDate getDatePlanted() {
//        return datePlanted;
//    }
//
//    public void setDatePlanted(LocalDate datePlanted) {
//        this.datePlanted = datePlanted;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
}