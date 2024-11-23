package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.HotelNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidPriceException;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApartmentService extends AbstractCrudService<Apartment, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentService.class);

    public ApartmentService() {
        super(Apartment.class);
    }

    public void registerApartment(double price, Hotel hotel) throws
            InvalidPriceException,
            HotelNotFoundException,
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to register apartment with price: {} and hotel: {}", price, hotel);
        Apartment apartment = new Apartment(price, hotel);
        Apartment savedApartment = create(apartment);
        LOGGER.info("Apartment successfully registered: {}", savedApartment);
    }

    public void deleteApartment(Apartment apartment) throws
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to delete apartment: {}", apartment);
        if (apartment == null || !existsById(apartment.getId())) {
            throw new ApartmentNotFoundException("Apartment does not exist or is invalid: " + apartment);
        }
        delete(apartment.getId());
        LOGGER.info("Apartment successfully deleted: {}", apartment);
    }
}
