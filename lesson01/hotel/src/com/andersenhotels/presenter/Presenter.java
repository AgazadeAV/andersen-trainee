package com.andersenhotels.presenter;

import com.andersenhotels.model.service.HotelService;

/**
 * The Presenter class serves as an intermediary between the user interface
 * and the hotel service layer. It provides methods to interact with the
 * HotelService, allowing for the management of apartments, reservations,
 * and listing functionality.
 */
public class Presenter {
    private HotelService hotelService;

    /**
     * Constructs a new Presenter instance, initializing the HotelService.
     */
    public Presenter() {
        this.hotelService = new HotelService();
    }

    /**
     * Gets the total count of registered apartments.
     *
     * @return the number of apartments.
     */
    public int getApartmentsCount() {
        return hotelService.getApartmentsCount();
    }

    /**
     * Registers a new apartment with the specified price.
     *
     * @param price the price of the apartment to be registered.
     */
    public void registerApartment(double price) {
        hotelService.registerApartment(price);
    }

    /**
     * Reserves an apartment for a client with the given ID and name.
     *
     * @param id the ID of the apartment to reserve.
     * @param clientName the name of the client making the reservation.
     */
    public void reserveApartment(int id, String clientName) {
        hotelService.reserveApartment(id, clientName);
    }

    /**
     * Releases an apartment with the given ID, canceling its reservation.
     *
     * @param id the ID of the apartment to release.
     */
    public void releaseApartment(int id) {
        hotelService.releaseApartment(id);
    }

    /**
     * Lists the apartments on the specified page with a defined page size.
     *
     * @param page the page number to display.
     * @param pageSize the number of apartments to display per page.
     */
    public void listApartments(int page, int pageSize) {
        hotelService.listApartments(page, pageSize);
    }
}
