package com.andersenhotels.model.service;

import com.andersenhotels.model.*;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.presenter.exceptions.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Comparator;
import java.util.List;

public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @Getter
    private final Hotel hotel;
    private final ValueValidator valueValidator;
    private final EntityManagerFactory entityManagerFactory;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
        this.valueValidator = new ValueValidator(this);
        this.entityManagerFactory = Persistence.createEntityManagerFactory("andersen-hotels");
    }

    public boolean apartmentExists(int id) {
        return hotel.getApartmentMap().containsKey(id);
    }

    public int getApartmentsCount() {
        return hotel.getApartments().size();
    }

    public int getReservedApartmentsCount() {
        return (int) hotel.getApartments().stream()
                .filter(apartment -> apartment.getStatus() == ApartmentStatus.RESERVED)
                .count();
    }

    public int getTotalPages() {
        int pageSize = ConfigManager.getPageSizeForPagination();
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0.");
        }
        return (int) Math.ceil((double) getApartmentsCount() / pageSize);
    }

    public void registerApartment(double price) {
        validatePrice(price);

        Apartment apartment = createApartment(price);

        saveApartmentToDatabase(apartment);

        hotel.addApartment(apartment);
        LOGGER.info("Apartment registered: ID = {}, Price = {}", apartment.getId(), apartment.getPrice());
    }

    private void validatePrice(double price) {
        if (price < 0) {
            LOGGER.warn("Attempt to register an apartment with a negative price: {}", price);
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
    }

    private Apartment createApartment(double price) {
        Apartment apartment = new Apartment();
        apartment.setPrice(price);
        apartment.setStatus(ApartmentStatus.AVAILABLE);
        return apartment;
    }

    private void saveApartmentToDatabase(Apartment apartment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(apartment);
            entityManager.getTransaction().commit();
            LOGGER.info("Apartment persisted with ID = {}", apartment.getId());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to persist apartment", e);
        } finally {
            entityManager.close();
        }
    }

    public void reserveApartment(int id, String guestName) {
        LOGGER.info("Attempting to reserve apartment with ID {} for guest '{}'", id, guestName);

        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = getApartmentById(id);

        if (apartment.getStatus() != ApartmentStatus.AVAILABLE) {
            throw new ApartmentNotReservedException("Apartment is already reserved.");
        }

        createAndAddReservation(apartment, guestName);
    }

    private Apartment getApartmentById(int id) {
        Apartment apartment = hotel.getApartmentMap().get(id);
        if (apartment == null) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID.");
        }
        return apartment;
    }

    private void createAndAddReservation(Apartment apartment, String guestName) {
        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation();
        reservation.setApartment(apartment);
        reservation.setGuest(guest);

        apartment.setStatus(ApartmentStatus.RESERVED);
        hotel.addReservation(reservation);

        LOGGER.info("Apartment reserved: ID = {}, Guest = {}", apartment.getId(), guestName);
    }

    public void releaseApartment(int id) {
        LOGGER.info("Attempting to release apartment with ID {}", id);

        valueValidator.validateApartmentId(id);

        Reservation reservation = getReservationByApartmentId(id);

        reservation.getApartment().setStatus(ApartmentStatus.AVAILABLE);
        hotel.getReservations().remove(reservation);

        LOGGER.info("Apartment released: ID = {}", id);
    }

    private Reservation getReservationByApartmentId(int id) {
        return hotel.getReservations().stream()
                .filter(reservation -> reservation.getApartment().getId() == id)
                .findFirst()
                .orElseThrow(() -> new ApartmentNotReservedException("Apartment is not reserved."));
    }

    public List<Apartment> listApartments(int page) {
        LOGGER.info("Listing apartments for page {}", page);

        validatePageNumber(page);

        int pageSize = ConfigManager.getPageSizeForPagination();
        return hotel.getApartments().stream()
                .sorted(Comparator.comparingInt(Apartment::getId))
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    private void validatePageNumber(int page) {
        if (page <= 0 || page > getTotalPages()) {
            throw new ApartmentNotFoundException("Invalid page number.");
        }
    }

    public void changeApartmentStatus(int id, ApartmentStatus newStatus) {
        LOGGER.info("Changing status of apartment ID {} to {}", id, newStatus);

        if (!ConfigManager.isAllowApartmentStatusChange()) {
            throw new UnsupportedOperationException("Changing apartment status is disabled by configuration.");
        }

        Apartment apartment = getApartmentById(id);
        apartment.setStatus(newStatus);

        LOGGER.info("Apartment status changed: ID = {}, New Status = {}", id, newStatus);
    }
}
