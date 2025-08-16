package com.example.demo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TREE")
public class Tree extends Plant {
}
