package com.andersenhotels.model.service;

import com.andersenhotels.model.Guest;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuestService extends AbstractCrudService<Guest, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestService.class);

    public GuestService() {
        super(Guest.class);
    }

    public Guest registerGuest(String name) throws
            InvalidNameException,
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to register guest with name: {}", name);
        Guest guest = new Guest(name);
        Guest savedGuest = create(guest);
        LOGGER.info("Guest successfully registered: {}", savedGuest);
        return savedGuest;
    }

    public void deleteGuest(Guest guest) throws
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to delete guest: {}", guest);
        if (guest == null || !existsById(guest.getId())) {
            throw new GuestNotFoundException("Guest does not exist or is invalid: " + guest);
        }
        delete(guest.getId());
        LOGGER.info("Guest successfully deleted with ID: {}", guest.getId());
    }
}
