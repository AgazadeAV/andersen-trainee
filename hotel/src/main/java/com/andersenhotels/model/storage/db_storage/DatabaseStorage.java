package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.storage.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseStorage implements DataStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseStorage.class);
    private static final String NEXT_APARTMENT_ID_KEY = "nextApartmentId";

    // SQL query constants
    private static final String INSERT_APARTMENTS_QUERY =
            "INSERT INTO apartments (id, price, status) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE price = ?, status = ?";
    private static final String INSERT_RESERVATIONS_QUERY =
            "INSERT INTO reservations (apartment_id, guest_name) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE guest_name = ?";
    private static final String INSERT_METADATA_QUERY =
            "INSERT INTO metadata (key_name, value) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE value = ?";
    private static final String SELECT_APARTMENTS_QUERY =
            "SELECT id, price, status FROM apartments";
    private static final String SELECT_RESERVATIONS_QUERY =
            "SELECT apartment_id, guest_name FROM reservations";
    private static final String SELECT_METADATA_QUERY =
            "SELECT value FROM metadata WHERE key_name = ?";
    private static final String TABLE_EXISTENCE_CHECK_QUERY =
            "SELECT 1 FROM %s LIMIT 1";

    @Override
    public void saveState(HotelService hotelService) throws IOException {
        try (Connection connection = DatabaseConnectionPool.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try {
                LOGGER.info("Saving state to database...");
                saveApartments(hotelService.getApartments(), connection);
                LOGGER.info("Apartments saved: {}", hotelService.getApartments().size());

                saveReservations(hotelService.getReservations(), connection);
                LOGGER.info("Reservations saved: {}", hotelService.getReservations().size());

                saveNextApartmentId(hotelService.getNextApartmentId(), connection);
                LOGGER.info("Next apartment ID saved: {}", hotelService.getNextApartmentId());

                connection.commit();
                LOGGER.info("State saved successfully.");
            } catch (SQLException e) {
                connection.rollback();
                LOGGER.error("Failed to save state to database. Transaction rolled back.", e);
                throw new IOException("Failed to save state to database", e);
            }
        } catch (SQLException e) {
            LOGGER.error("Database connection error", e);
            throw new IOException("Database connection error", e);
        }
    }

    @Override
    public HotelService loadState() throws IOException {
        HotelService hotelService = new HotelService();

        try (Connection connection = DatabaseConnectionPool.getDataSource().getConnection()) {
            LOGGER.info("Loading state from database...");
            checkTablesExistence(connection);

            Map<Integer, Apartment> apartments = loadApartments(connection);
            LOGGER.info("Apartments loaded: {}", apartments.size());
            hotelService.setApartments(apartments);

            Map<Integer, Reservation> reservations = loadReservations(apartments, connection);
            LOGGER.info("Reservations loaded: {}", reservations.size());
            hotelService.setReservations(reservations);

            int nextApartmentId = loadNextApartmentId(connection);
            LOGGER.info("Next apartment ID loaded: {}", nextApartmentId);
            hotelService.setNextApartmentId(nextApartmentId);

            LOGGER.info("State loaded successfully.");
        } catch (SQLException e) {
            LOGGER.error("Failed to load state from database", e);
            throw new IOException("Failed to load state from database", e);
        }

        return hotelService;
    }

    @Override
    public void saveStateForTests(HotelService hotelService) throws IOException {
        saveState(hotelService);
    }

    @Override
    public HotelService loadStateForTests() throws IOException {
        return loadState();
    }

    private void saveApartments(Map<Integer, Apartment> apartments, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_APARTMENTS_QUERY)) {
            for (Apartment apartment : apartments.values()) {
                statement.setInt(1, apartment.getId());
                statement.setDouble(2, apartment.getPrice());
                statement.setString(3, apartment.getStatus().name());
                statement.setDouble(4, apartment.getPrice());
                statement.setString(5, apartment.getStatus().name());
                statement.addBatch();
                LOGGER.debug("Prepared batch for apartment ID: {}", apartment.getId());
            }
            statement.executeBatch();
        }
        LOGGER.info("All apartments saved to the database.");
    }

    private void saveReservations(Map<Integer, Reservation> reservations, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_RESERVATIONS_QUERY)) {
            for (Reservation reservation : reservations.values()) {
                statement.setInt(1, reservation.getApartment().getId());
                statement.setString(2, reservation.getGuest().getName());
                statement.setString(3, reservation.getGuest().getName());
                statement.addBatch();
                LOGGER.debug("Prepared batch for reservation: Apartment ID = {}, Guest = {}",
                        reservation.getApartment().getId(), reservation.getGuest().getName());
            }
            statement.executeBatch();
        }
        LOGGER.info("All reservations saved to the database.");
    }

    private void saveNextApartmentId(int nextApartmentId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_METADATA_QUERY)) {
            statement.setString(1, NEXT_APARTMENT_ID_KEY);
            statement.setInt(2, nextApartmentId);
            statement.setInt(3, nextApartmentId);
            statement.executeUpdate();
            LOGGER.debug("Next apartment ID saved: {}", nextApartmentId);
        }
    }

    private Map<Integer, Apartment> loadApartments(Connection connection) throws SQLException {
        Map<Integer, Apartment> apartments = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_APARTMENTS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");
                apartments.put(id, new Apartment(id, price, ApartmentStatus.valueOf(status.toUpperCase())));
                LOGGER.debug("Loaded apartment: ID = {}, Price = {}, Status = {}", id, price, status);
            }
        }
        return apartments;
    }

    private Map<Integer, Reservation> loadReservations(Map<Integer, Apartment> apartments, Connection connection) throws SQLException {
        Map<Integer, Reservation> reservations = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_RESERVATIONS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int apartmentId = resultSet.getInt("apartment_id");
                String guestName = resultSet.getString("guest_name");
                Apartment apartment = apartments.get(apartmentId);

                if (apartment != null) {
                    Guest guest = new Guest(guestName);
                    reservations.put(apartmentId, new Reservation(apartment, guest));
                    LOGGER.debug("Loaded reservation: Apartment ID = {}, Guest = {}", apartmentId, guestName);
                } else {
                    LOGGER.warn("Reservation refers to nonexistent apartment ID: {}", apartmentId);
                }
            }
        }
        return reservations;
    }

    private int loadNextApartmentId(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_METADATA_QUERY)) {
            statement.setString(1, NEXT_APARTMENT_ID_KEY);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("value");
                } else {
                    LOGGER.warn("No metadata found for next apartment ID. Defaulting to 1.");
                    return 1; // Default value
                }
            }
        }
    }

    private void checkTablesExistence(Connection connection) throws SQLException {
        String[] tables = {"apartments", "reservations", "metadata"};
        for (String table : tables) {
            String query = String.format(TABLE_EXISTENCE_CHECK_QUERY, table);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeQuery();
                LOGGER.debug("Table '{}' exists.", table);
            } catch (SQLException e) {
                LOGGER.warn("Table '{}' does not exist or is not accessible.", table, e);
                throw e;
            }
        }
    }
}
