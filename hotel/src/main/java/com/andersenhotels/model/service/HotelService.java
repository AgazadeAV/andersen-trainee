package com.andersenhotels.model.service;

import com.andersenhotels.model.*;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.presenter.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    private final Hotel hotel;
    private final EntityManagerFactory entityManagerFactory;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
        this.entityManagerFactory = Persistence.createEntityManagerFactory(ConfigManager.getPersistenceUnitName());
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
        ValueValidator.validatePrice(price);

        Apartment apartment = createApartment(price);

        executeTransaction(entityManager -> entityManager.persist(apartment));
        addToList(hotel.getApartments(), apartment, "Apartment already exists in the list.");

        LOGGER.info("Apartment registered: ID = {}, Price = {}", apartment.getId(), apartment.getPrice());
    }

    public void reserveApartment(int id, String guestName) {
        LOGGER.info("Attempting to reserve apartment with ID {} for guest '{}'", id, guestName);

        ValueValidator.validateGuestName(guestName);
        Apartment apartment = findById(hotel.getApartments(),
                apartmentForSearch -> apartmentForSearch.getId() == id,
                new ApartmentNotFoundException("Apartment not found for the given ID."));
        ValueValidator.validateApartmentStatus(apartment, ApartmentStatus.AVAILABLE);

        Guest guest = new Guest();
        guest.setName(guestName);

        Reservation reservation = new Reservation();
        reservation.setApartment(apartment);
        reservation.setGuest(guest);

        apartment.setStatus(ApartmentStatus.RESERVED);
        addToList(hotel.getReservations(), reservation, "Reservation already exists for this apartment.");

        LOGGER.info("Apartment reserved: ID = {}, Guest = {}", apartment.getId(), guestName);
    }

    public void releaseApartment(int id) {
        LOGGER.info("Attempting to release apartment with ID {}", id);

        Reservation reservation = findById(hotel.getReservations(),
                r -> r.getApartment().getId() == id,
                new ApartmentNotReservedException("Apartment is not reserved."));
        reservation.getApartment().setStatus(ApartmentStatus.AVAILABLE);

        removeFromList(hotel.getReservations(), reservation, "Reservation does not exist.");

        LOGGER.info("Apartment released: ID = {}", id);
    }

    public List<Apartment> listApartments(int page) {
        LOGGER.info("Listing apartments for page {}", page);

        ValueValidator.validatePageNumber(page, getTotalPages());

        int pageSize = ConfigManager.getPageSizeForPagination();
        return hotel.getApartments().stream()
                .sorted(Comparator.comparingInt(Apartment::getId))
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public void changeApartmentStatus(int id, ApartmentStatus newStatus) {
        LOGGER.info("Changing status of apartment ID {} to {}", id, newStatus);

        if (!ConfigManager.isAllowApartmentStatusChange()) {
            throw new UnsupportedOperationException("Changing apartment status is disabled by configuration.");
        }

        Apartment apartment = findById(hotel.getApartments(), a -> a.getId() == id,
                new ApartmentNotFoundException("Apartment not found for the given ID."));
        apartment.setStatus(newStatus);

        LOGGER.info("Apartment status changed: ID = {}, New Status = {}", id, newStatus);
    }

    private Apartment createApartment(double price) {
        Apartment apartment = new Apartment();
        apartment.setPrice(price);
        apartment.setStatus(ApartmentStatus.AVAILABLE);
        return apartment;
    }

    private <T> T findById(List<T> list, Predicate<T> predicate, RuntimeException exception) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> exception);
    }

    private <T> void addToList(List<T> list, T item, String errorMessage) {
        if (list.contains(item)) {
            throw new IllegalStateException(errorMessage);
        }
        list.add(item);
    }

    private <T> void removeFromList(List<T> list, T item, String errorMessage) {
        if (!list.remove(item)) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private void executeTransaction(EntityManagerAction action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            action.execute(entityManager);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            LOGGER.error("Transaction failed, rolling back.", e);
        } finally {
            entityManager.close();
        }
    }

    @FunctionalInterface
    private interface EntityManagerAction {
        void execute(EntityManager entityManager);
    }
}
