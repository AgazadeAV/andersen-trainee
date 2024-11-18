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

    private static final Logger logger = LoggerFactory.getLogger(DatabaseStorage.class);
    private static final String NEXT_APARTMENT_ID_KEY = "nextApartmentId";

    @Override
    public void saveState(HotelService hotelService) throws IOException {
        try (Connection connection = DatabaseConnectionPool.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try {
                saveApartments(hotelService.getApartments(), connection);
                saveReservations(hotelService.getReservations(), connection);
                saveNextApartmentId(hotelService.getNextApartmentId(), connection);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Failed to save state to database", e);
                throw new IOException("Failed to save state to database", e);
            }
        } catch (SQLException e) {
            logger.error("Database connection error", e);
            throw new IOException("Database connection error", e);
        }
    }

    @Override
    public HotelService loadState() throws IOException {
        HotelService hotelService = new HotelService();

        try (Connection connection = DatabaseConnectionPool.getDataSource().getConnection()) {
            checkTablesExistence(connection);

            Map<Integer, Apartment> apartments = loadApartments(connection);
            hotelService.setApartments(apartments);

            Map<Integer, Reservation> reservations = loadReservations(apartments, connection);
            hotelService.setReservations(reservations);

            int nextApartmentId = loadNextApartmentId(connection);
            hotelService.setNextApartmentId(nextApartmentId);

        } catch (SQLException e) {
            logger.error("Failed to load state from database", e);
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
        String query = "INSERT INTO apartments (id, price, status) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE price = ?, status = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Apartment apartment : apartments.values()) {
                statement.setInt(1, apartment.getId());
                statement.setDouble(2, apartment.getPrice());
                statement.setString(3, apartment.getStatus().name());
                statement.setDouble(4, apartment.getPrice());
                statement.setString(5, apartment.getStatus().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void saveReservations(Map<Integer, Reservation> reservations, Connection connection) throws SQLException {
        String query = "INSERT INTO reservations (apartment_id, guest_name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE guest_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Reservation reservation : reservations.values()) {
                statement.setInt(1, reservation.getApartment().getId());
                statement.setString(2, reservation.getGuest().getName());
                statement.setString(3, reservation.getGuest().getName());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void saveNextApartmentId(int nextApartmentId, Connection connection) throws SQLException {
        String query = "INSERT INTO metadata (key_name, value) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE value = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, NEXT_APARTMENT_ID_KEY);
            statement.setInt(2, nextApartmentId);
            statement.setInt(3, nextApartmentId);
            statement.executeUpdate();
        }
    }

    private Map<Integer, Apartment> loadApartments(Connection connection) throws SQLException {
        String query = "SELECT id, price, status FROM apartments";
        Map<Integer, Apartment> apartments = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");
                apartments.put(id, new Apartment(id, price, ApartmentStatus.valueOf(status.toUpperCase())));
            }
        }

        return apartments;
    }

    private Map<Integer, Reservation> loadReservations(Map<Integer, Apartment> apartments, Connection connection) throws SQLException {
        String query = "SELECT apartment_id, guest_name FROM reservations";
        Map<Integer, Reservation> reservations = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int apartmentId = resultSet.getInt("apartment_id");
                String guestName = resultSet.getString("guest_name");
                Apartment apartment = apartments.get(apartmentId);

                if (apartment != null) {
                    Guest guest = new Guest(guestName);
                    reservations.put(apartmentId, new Reservation(apartment, guest));
                }
            }
        }

        return reservations;
    }

    private int loadNextApartmentId(Connection connection) throws SQLException {
        String query = "SELECT value FROM metadata WHERE key_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, NEXT_APARTMENT_ID_KEY);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("value");
                } else {
                    return 1; // The default value if the record is not found
                }
            }
        }
    }

    private void checkTablesExistence(Connection connection) throws SQLException {
        String[] tables = {"apartments", "reservations", "metadata"};
        for (String table : tables) {
            String query = "SELECT 1 FROM " + table + " LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeQuery();
            } catch (SQLException e) {
                logger.warn("Table '{}' does not exist or is not accessible.", table, e);
                throw e;
            }
        }
    }
}
