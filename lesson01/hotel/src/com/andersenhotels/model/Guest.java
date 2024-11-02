package com.andersenhotels.model;

/**
 * Represents a guest who may reserve an apartment.
 */
public class Guest {
    private String name;

    /**
     * Constructs a new Guest with the specified name.
     *
     * @param name the name of the guest.
     */
    public Guest(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the guest.
     *
     * @return the name of the guest.
     */
    public String getName() {
        return name;
    }
}
