package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable=false)
    private String password;

    @NotNull
    @Column(name = "email", nullable=false)
    private String email;

    @Nullable
    @Column(name = "first_name")
    private String first_name;

    @Nullable
    @Column(name = "last_name")
    private String last_name;

    @NotNull
    @Column(name = "created_on", nullable=false, updatable = false)
    private LocalDateTime created_on;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference("user-garden")
    private List<Garden> gardenList = new ArrayList<>();


    @Nullable
    public List<Garden> getGardenList() {
        return gardenList;
    }

    public void setGardenList(@Nullable List<Garden> gardenList) {
        this.gardenList = gardenList;
    }



    public User() {
        this.created_on = LocalDateTime.now();
    }

    public User(String username, String password, String email, @Nullable String first_name, @Nullable String last_name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.created_on = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(@Nullable String first_name) {
        this.first_name = first_name;
    }

    @Nullable
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(@Nullable String last_name) {
        this.last_name = last_name;
    }

    public LocalDateTime getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDateTime created_on) {
        this.created_on = created_on;
    }
}
