package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApartmentService extends AbstractCrudService<Apartment, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentService.class);

    public ApartmentService() {
        super(Apartment.class);
    }

    public void registerApartment(double price, Hotel hotel) {
        LOGGER.info("Attempting to register apartment with price: {} and hotel: {}", price, hotel);
        try {
            Apartment apartment = new Apartment(price, hotel);
            Apartment savedApartment = create(apartment);
            LOGGER.info("Apartment successfully registered: {}", savedApartment);
        } catch (Exception e) {
            LOGGER.error("Failed to register apartment with price {} and hotel {}: {}", price, hotel, e.getMessage(), e);
            throw e;
        }
    }

    public void deleteApartment(Apartment apartment) {
        LOGGER.info("Attempting to delete apartment: {}", apartment);
        try {
            if (apartment == null || !existsById(apartment.getId())) {
                throw new IllegalArgumentException("Apartment does not exist or is invalid: " + apartment);
            }
            delete(apartment.getId());
            LOGGER.info("Apartment successfully deleted: {}", apartment);
        } catch (Exception e) {
            LOGGER.error("Failed to delete apartment {}: {}", apartment, e.getMessage(), e);
            throw e;
        }
    }
}
