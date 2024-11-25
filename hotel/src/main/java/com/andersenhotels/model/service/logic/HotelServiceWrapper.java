package com.andersenhotels.model.service.logic;

import com.andersenhotels.model.*;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.service.ApartmentService;
import com.andersenhotels.model.service.GuestService;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.service.ReservationService;
import com.andersenhotels.presenter.exceptions.*;
import jakarta.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HotelServiceWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceWrapper.class);

    private final ApartmentService apartmentService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final HotelService hotelService;

    public HotelServiceWrapper() {
        this.apartmentService = new ApartmentService();
        this.guestService = new GuestService();
        this.reservationService = new ReservationService();
        this.hotelService = new HotelService();
    }

    public Hotel initializeHotel() throws IllegalArgumentException, PersistenceException {
        LOGGER.info("Initializing hotel...");
        return hotelService.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Hotel newHotel = new Hotel();
                    hotelService.create(newHotel);
                    LOGGER.info("No existing hotel found. A new hotel has been created with ID: {}", newHotel.getId());
                    return newHotel;
                });
    }

    public void registerApartment(double price, Hotel hotel) throws
            InvalidPriceException,
            HotelNotFoundException,
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Registering a new apartment with price: {}", price);
        apartmentService.registerApartment(price, hotel);
        LOGGER.info("Apartment successfully registered with price: {}", price);
    }

    public void reserveApartment(Hotel hotel, int apartmentId, String guestName) throws
            InvalidNameException,
            ApartmentNotFoundException,
            GuestNotFoundException,
            ApartmentAlreadyReservedException,
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Reserving apartment ID {} for guest '{}'.", apartmentId, guestName);
        Apartment apartment = apartmentService.read(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found for the given ID."));

        Guest guest = guestService.registerGuest(guestName);
        LOGGER.info("Guest registered: {}", guest);

        Reservation reservation = reservationService.createReservation(apartment, guest);
        LOGGER.info("Reservation created: {}", reservation);

        apartmentService.update(apartment);
        hotel.getReservations().add(reservation);
        LOGGER.info("Apartment ID {} reserved for guest '{}'.", apartmentId, guestName);
    }

    public void releaseApartment(Hotel hotel, int reservationId) throws
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Releasing apartment for reservation ID: {}", reservationId);
        Reservation reservation = reservationService.read(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found for the given ID."));

        reservationService.cancelReservation(reservation);
        hotel.getReservations().remove(reservation);
        LOGGER.info("Reservation ID {} successfully canceled and apartment released.", reservationId);
    }

    public int getTotalPages() throws PersistenceException {
        LOGGER.info("Calculating total pages...");
        int pageSize = ConfigManager.getPageSizeForPagination();
        long totalApartments = apartmentService.findAll().size();
        int totalPages = (int) Math.ceil((double) totalApartments / pageSize);
        LOGGER.info("Total pages calculated: {} (Total apartments: {}, Page size: {}).", totalPages, totalApartments, pageSize);
        return totalPages;
    }

    public List<Apartment> listApartments(int page, int pageSize) throws PersistenceException {
        LOGGER.info("Listing apartments for page: {} with page size: {}.", page, pageSize);
        int totalPages = getTotalPages();
        if (page <= 0 || page > totalPages) {
            LOGGER.warn("Invalid page number: {}. Total pages available: {}.", page, totalPages);
            throw new WrongPageNumberException("Invalid page number.");
        }
        List<Apartment> apartments = apartmentService.findAll().stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
        LOGGER.info("Apartments listed for page {}: {} items found.", page, apartments.size());
        return apartments;
    }
}
