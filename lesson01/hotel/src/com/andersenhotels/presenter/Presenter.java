package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.exception.*;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.view.common.View;

import java.util.List;

/**
 * The `Presenter` class serves as the mediator between the `View` (UI layer) and `HotelService` (business logic layer).
 * It coordinates user commands received from the view and forwards them to the `HotelService` for processing.
 * It then communicates the results or errors back to the `View`.
 */
public class Presenter {
    private HotelService hotelService; // Manages core hotel operations such as apartment registration and reservation
    private View view; // Interface to communicate with the view layer for displaying messages and errors

    /**
     * Constructs a new Presenter, initializing it with the given View instance and a new HotelService instance.
     *
     * @param view The View instance used to interact with the UI.
     */
    public Presenter(View view) {
        this.hotelService = new HotelService();
        this.view = view;
    }

    /**
     * Registers a new apartment with the specified price by calling the HotelService.
     * Displays a success message or an error if the price is invalid.
     *
     * @param price The price of the apartment to register.
     * @throws InvalidPriceException if the price is negative.
     */
    public void registerApartment(double price) {
        try {
            hotelService.registerApartment(price);
            view.displayMessage("Apartment registered successfully with price: " + price + ".");
        } catch (InvalidPriceException e) {
            view.displayError(e.getMessage());
        }
    }

    /**
     * Reserves an apartment by its ID for a client with the specified name.
     * Displays a success message if successful, or an error if the reservation fails.
     *
     * @param id         The ID of the apartment to reserve.
     * @param clientName The name of the client reserving the apartment.
     * @throws ApartmentNotFoundException if the apartment is not found.
     * @throws ApartmentAlreadyReservedException if the apartment is already reserved.
     * @throws InvalidApartmentIdException if the apartment ID is invalid.
     * @throws InvalidNameException if the client name is invalid.
     */
    public void reserveApartment(int id, String clientName) {
        try {
            hotelService.reserveApartment(id, clientName);
            view.displayMessage("Apartment " + id + " reserved successfully for client: " + clientName + ".");
        } catch (ApartmentNotFoundException | ApartmentAlreadyReservedException |
                 InvalidApartmentIdException | InvalidNameException e) {
            view.displayError(e.getMessage());
        }
    }

    /**
     * Releases a reserved apartment by its ID.
     * Displays a success message if the release is successful, or an error if the release fails.
     *
     * @param id The ID of the apartment to release.
     * @throws ApartmentNotFoundException if the apartment is not found.
     * @throws ApartmentNotReservedException if the apartment is not reserved.
     * @throws InvalidApartmentIdException if the apartment ID is invalid.
     */
    public void releaseApartment(int id) {
        try {
            hotelService.releaseApartment(id);
            view.displayMessage("Apartment " + id + " released successfully.");
        } catch (ApartmentNotFoundException | ApartmentNotReservedException | InvalidApartmentIdException e) {
            view.displayError(e.getMessage());
        }
    }

    /**
     * Lists apartments based on the specified page and page size parameters.
     * Retrieves a paginated list of apartments from the `HotelService` and displays each one.
     * If no apartments are found, displays an error.
     *
     * @param page     The page number to display.
     * @param pageSize The number of apartments per page.
     * @throws ApartmentNotFoundException if no apartments are found for the given page and page size.
     */
    public void listApartments(int page, int pageSize) {
        try {
            List<Apartment> apartments = hotelService.listApartments(page, pageSize);
            for (Apartment apartment : apartments) {
                view.displayMessage(String.format("%s", apartment));
            }
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
        }
    }
}
