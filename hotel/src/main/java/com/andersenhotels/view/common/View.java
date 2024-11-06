package com.andersenhotels.view.common;

import java.util.List;

/**
 * The `View` interface defines the contract for a user interface in the hotel management application.
 * It provides methods to initiate and terminate application workflows, manage apartment-related operations,
 * and display messages and errors to the user.
 * <p>
 * Implementations of this interface handle the interaction between the user and the application's core functionality.
 */
public interface View {

    /**
     * Initiates the main workflow of the application, typically displaying
     * a welcome message and a main menu to the user.
     * Implementations should ensure this method triggers the primary interactions
     * between the user and the application.
     */
    void startWork();

    /**
     * Ends the main workflow of the application, performing any necessary
     * cleanup or shutdown operations. This method may display a farewell
     * message before exiting.
     */
    void finishWork();

    /**
     * Prompts the user to register a new apartment in the system.
     * Implementations should request required apartment details (e.g., price)
     * from the user and pass this information to the application's backend.
     */
    boolean registerApartment();

    /**
     * Initiates the process of reserving an apartment for a guest.
     * Implementations should prompt the user for the apartment ID and guest name,
     * then proceed to create a reservation if the inputs are valid.
     */
    boolean reserveApartment();

    /**
     * Initiates the process of releasing a reserved apartment.
     * Implementations should prompt the user for the apartment ID,
     * validate the input, and release the reservation if appropriate.
     */
    boolean releaseApartment();

    /**
     * Lists available apartments in the system, supporting pagination
     * by allowing the user to specify a page number and page size.
     * Implementations should display apartment information in a readable format.
     */
    List<String> listApartments();

    /**
     * Displays a generic message to the user.
     * This method is used for informational or confirmation messages.
     *
     * @param message The message to display to the user.
     */
    void displayMessage(String message);

    /**
     * Displays an error message to the user.
     * This method is typically called when an error or exception occurs,
     * alerting the user to the issue in a clear and understandable format.
     *
     * @param errorMessage The error message to display to the user.
     */
    void displayError(String errorMessage);
}
