package com.andersenhotels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guests")
@Setter
@Getter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    public Guest() {
    }

    public Guest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Guest ID: " + id + ", Name: " + name;
    }
}
