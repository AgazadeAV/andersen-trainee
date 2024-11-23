package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.InvalidNameException;
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
        // Default constructor for JPA
    }

    public Guest(String name) {
        if (name == null || name.trim().isEmpty() || Character.isDigit(name.charAt(0))) {
            throw new InvalidNameException("Guest name cannot be null, empty, " +
                    "only whitespace or start with a number.");
        }

        this.name = name.trim();
    }

    @Override
    public String toString() {
        return String.format("Guest ID: %d, Name: %s", id, name);
    }
}
