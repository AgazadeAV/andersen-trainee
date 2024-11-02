package com.andersenhotels.view.common;

/**
 * The View interface defines the basic methods for user interaction
 * in the hotel management application. Implementing classes should
 * provide concrete behaviors for each method.
 */
public interface View {
    /**
     * Starts the user interface for interaction.
     */
    void startWork();

    /**
     * Ends the user interface interaction.
     */
    void finishWork();

    /**
     * Prompts the user to register a new apartment.
     */
    void registerApartment();

    /**
     * Prompts the user to reserve an apartment.
     */
    void reserveApartment();

    /**
     * Prompts the user to release an apartment.
     */
    void releaseApartment();

    /**
     * Prompts the user to list available apartments.
     */
    void listApartments();
}
