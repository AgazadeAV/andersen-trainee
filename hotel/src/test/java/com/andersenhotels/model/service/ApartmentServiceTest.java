package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.HotelNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidPriceException;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApartmentServiceTest {

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private Hotel mockHotel;

    @Mock
    private Apartment mockApartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        apartmentService = spy(new ApartmentService());
    }

    @Test
    void registerApartment_Success() {
        double price = 100.0;
        Apartment apartment = new Apartment(price, mockHotel);

        doReturn(apartment).when(apartmentService).create(any(Apartment.class));

        assertDoesNotThrow(() -> apartmentService.registerApartment(price, mockHotel));
        verify(apartmentService).create(any(Apartment.class));
    }

    @Test
    void registerApartment_InvalidPrice() {
        double invalidPrice = -50.0;

        assertThrows(InvalidPriceException.class, () -> apartmentService.registerApartment(invalidPrice, mockHotel));
        verify(apartmentService, never()).create(any(Apartment.class));
    }

    @Test
    void registerApartment_NullHotel() {
        double price = 100.0;

        assertThrows(HotelNotFoundException.class, () -> apartmentService.registerApartment(price, null));
        verify(apartmentService, never()).create(any(Apartment.class));
    }

    @Test
    void deleteApartment_Success() {
        int apartmentId = 1;
        when(mockApartment.getId()).thenReturn(apartmentId);
        doReturn(true).when(apartmentService).existsById(apartmentId);
        doNothing().when(apartmentService).delete(apartmentId);

        assertDoesNotThrow(() -> apartmentService.deleteApartment(mockApartment));
        verify(apartmentService).delete(apartmentId);
    }

    @Test
    void deleteApartment_NotFound() {
        int apartmentId = 1;
        when(mockApartment.getId()).thenReturn(apartmentId);
        doReturn(false).when(apartmentService).existsById(apartmentId);

        assertThrows(ApartmentNotFoundException.class, () -> apartmentService.deleteApartment(mockApartment));
        verify(apartmentService, never()).delete(anyInt());
    }

    @Test
    void deleteApartment_NullApartment() {
        assertThrows(ApartmentNotFoundException.class, () -> apartmentService.deleteApartment(null));
        verify(apartmentService, never()).delete(anyInt());
    }

    @Test
    void registerApartment_PersistenceException() {
        double price = 100.0;

        doThrow(PersistenceException.class).when(apartmentService).create(any(Apartment.class));

        assertThrows(PersistenceException.class, () -> apartmentService.registerApartment(price, mockHotel));
        verify(apartmentService).create(any(Apartment.class));
    }

    @Test
    void deleteApartment_PersistenceException() {
        int apartmentId = 1;
        when(mockApartment.getId()).thenReturn(apartmentId);
        doReturn(true).when(apartmentService).existsById(apartmentId);
        doThrow(PersistenceException.class).when(apartmentService).delete(apartmentId);

        assertThrows(PersistenceException.class, () -> apartmentService.deleteApartment(mockApartment));
        verify(apartmentService).delete(apartmentId);
    }
}
