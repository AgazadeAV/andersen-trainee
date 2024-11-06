package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.view.common.View;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Presenter` class acts as an intermediary between the `View` (UI layer) and `HotelService` (business logic layer).
 * It coordinates the user interactions received from the view layer, forwards them to the `HotelService` for processing,
 * and communicates the results or any errors back to the `View`.
 * <p>
 * This class is responsible for ensuring that the UI remains decoupled from the business logic by handling all
 * interactions with the `HotelService` and managing any exceptions or errors that occur during operations.
 */
public class Presenter {
    private HotelService hotelService;
    private View view;

    /**
     * Constructs a new Presenter, initializing it with a provided View instance and creating a new HotelService instance.
     *
     * @param view The View instance used to interact with the UI. This is where messages and error notifications
     *             will be displayed to the user.
     */
    public Presenter(View view) {
        this.hotelService = new HotelService();
        this.view = view;
    }

    /**
     * Constructs a new `Presenter` using a specified `View` and a mockable `HotelService` instance.
     * <p>
     * This constructor is intended primarily for testing purposes, allowing the injection
     * of a mock `HotelService` to facilitate controlled testing scenarios without relying
     * on the production service behavior. By using this constructor, unit tests can verify
     * the behavior of the `Presenter` class while simulating interactions with `HotelService`
     * and `View`, making it possible to inject mocks and control responses.
     * <p>
     * In the production environment, use the primary constructor {@link #Presenter(View)}
     * that instantiates its own `HotelService`.
     *
     * @param view         The `View` instance used for user interaction in the UI layer. It displays
     *                     messages and errors to the user.
     * @param hotelService The `HotelService` instance, typically mocked during testing to verify and control
     *                     interactions with the business logic layer.
     */
    public Presenter(View view, HotelService hotelService) {
        this.hotelService = hotelService;
        this.view = view;
    }


    /**
     * Registers a new apartment with a specified rental price by invoking the `HotelService`.
     * If the registration is successful, a success message is displayed to the user.
     * If the price is invalid, an error message is displayed instead.
     *
     * @param price The price of the apartment to register. Must be a positive number.
     * @return `true` if the apartment was successfully registered, `false` if an error occurred.
     * @throws InvalidPriceException if the provided price is negative, resulting in a failed registration.
     */
    public boolean registerApartment(double price) {
        try {
            hotelService.registerApartment(price);
            return true;
        } catch (InvalidPriceException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    /**
     * Reserves an apartment for a client by its ID and associates it with the client's name.
     * If successful, the apartment is marked as reserved. Any errors encountered, such as
     * an invalid ID or already reserved apartment, are displayed as error messages.
     *
     * @param id         The ID of the apartment to reserve. Expected to be within the range of registered apartments.
     * @param guestName The name of the client reserving the apartment. Should be a non-null, valid name.
     * @return `true` if the reservation was successfully created, `false` if an error occurred.
     * @throws ApartmentNotFoundException        if the apartment with the given ID does not exist.
     * @throws ApartmentAlreadyReservedException if the apartment is already reserved.
     * @throws InvalidApartmentIdException       if the provided apartment ID is invalid.
     * @throws InvalidNameException              if the client name is invalid (e.g., null or improperly formatted).
     */
    public boolean reserveApartment(int id, String guestName) {
        try {
            hotelService.reserveApartment(id, guestName);
            return true;
        } catch (ApartmentNotFoundException | ApartmentAlreadyReservedException |
                 InvalidApartmentIdException | InvalidNameException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    /**
     * Releases a reserved apartment based on its ID, marking it as available once again.
     * Displays a success message upon successful release, or an error message if the release fails.
     *
     * @param id The ID of the apartment to release. Should correspond to a currently reserved apartment.
     * @return `true` if the apartment was successfully released, `false` if an error occurred.
     * @throws ApartmentNotFoundException    if the apartment with the specified ID does not exist.
     * @throws ApartmentNotReservedException if the apartment is not currently reserved.
     * @throws InvalidApartmentIdException   if the provided apartment ID is invalid.
     */
    public boolean releaseApartment(int id) {
        try {
            hotelService.releaseApartment(id);
            return true;
        } catch (ApartmentNotFoundException | ApartmentNotReservedException | InvalidApartmentIdException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a paginated list of apartments for the specified page, then formats and
     * displays each apartment. If no apartments are available for the requested page,
     * an error message is displayed.
     *
     * @param page The page number to display. Must be a positive integer within the range of available pages.
     * @return A list of string representations of apartments on the specified page.
     * @throws ApartmentNotFoundException if no apartments are found for the specified page.
     */
    public List<String> listApartments(int page) {
        List<String> apartmentList = new ArrayList<>();
        try {
            List<Apartment> apartments = hotelService.listApartments(page);
            for (Apartment apartment : apartments) {
                apartmentList.add(apartment.toString());
            }
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
        }
        return apartmentList;
    }

    /**
     * Retrieves the total number of pages available for apartment listings.
     * This method is useful for the UI to display pagination options.
     *
     * @return The total number of pages, based on the number of apartments and the defined page size.
     */
    public int getTotalPages() {
        return hotelService.getTotalPages();
    }
}
