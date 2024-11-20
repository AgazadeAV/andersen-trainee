package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.storage.DataStorage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DatabaseStorage implements DataStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseStorage.class);
    private static final String PERSISTENCE_UNIT_NAME = "andersen-hotels";
    private final EntityManagerFactory emf;

    public DatabaseStorage() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        LOGGER.info("EntityManagerFactory initialized for persistence unit '{}'", PERSISTENCE_UNIT_NAME);
    }

    @Override
    public void saveState(Hotel hotel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            LOGGER.info("Saving hotel state to the database...");
            em.merge(hotel);
            em.getTransaction().commit();
            LOGGER.info("Hotel state saved successfully.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error("Failed to save hotel state. Transaction rolled back.", e);
            throw new RuntimeException("Failed to save state", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Hotel loadState() {
        EntityManager em = emf.createEntityManager();
        try {
            LOGGER.info("Loading hotel state from the database...");
            List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
            LOGGER.info("Loaded {} hotels from the database.", hotels.size());
            return hotels.isEmpty() ? new Hotel() : hotels.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void saveStateForTests(Hotel hotel) {
        saveState(hotel);
    }

    @Override
    public Hotel loadStateForTests() {
        return loadState();
    }
}