package com.andersenhotels.model.storage;

import lombok.Getter;

@Getter
public enum DataStorageType {
    JSON("JSON Storage"),
    DATABASE("Database Storage");

    private final String description;

    DataStorageType(String description) {
        this.description = description;
    }
}
