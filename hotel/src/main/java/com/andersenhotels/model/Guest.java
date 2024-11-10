package com.andersenhotels.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Guest {

    private String name;

    public Guest(String name) {
        this.name = name;
    }
}
