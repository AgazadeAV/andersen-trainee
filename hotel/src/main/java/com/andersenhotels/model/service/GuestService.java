package com.andersenhotels.model.service;

import com.andersenhotels.model.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuestService extends AbstractCrudService<Guest, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestService.class);

    public GuestService() {
        super(Guest.class);
    }

    public Guest registerGuest(String name) {
        LOGGER.info("Attempting to register guest with name: {}", name);
        try {
            Guest guest = new Guest(name);
            Guest savedGuest = create(guest);
            LOGGER.info("Guest successfully registered: {}", savedGuest);
            return savedGuest;
        } catch (Exception e) {
            LOGGER.error("Failed to register guest with name {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }

    public void deleteGuest(Guest guest) {
        LOGGER.info("Attempting to delete guest: {}", guest);
        try {
            if (guest == null || !existsById(guest.getId())) {
                throw new IllegalArgumentException("Guest does not exist or is invalid: " + guest);
            }
            delete(guest.getId());
            LOGGER.info("Guest successfully deleted with ID: {}", guest.getId());
        } catch (Exception e) {
            LOGGER.error("Failed to delete guest {}: {}", guest, e.getMessage(), e);
            throw e;
        }
    }
}
