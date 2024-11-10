package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.view.common.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresenterTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private View view;

    private Presenter presenter;

    private static int ID = 1;
    private static String CLIENT_NAME = "Azer Agazade";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new Presenter(view, hotelService);
    }

    @Test
    void registerApartment_Success() {
        double price = 100.0;

        assertTrue(presenter.registerApartment(price));
        verify(hotelService, times(1)).registerApartment(price);
    }

    @Test
    void registerApartment_InvalidPrice() {
        double price = -50.0;

        doThrow(new InvalidPriceException("The price should be a positive number."))
                .when(hotelService).registerApartment(price);

        assertFalse(presenter.registerApartment(price));
        verify(view, times(1)).
                displayError("The price should be a positive number.");
    }

    @Test
    void reserveApartment_Success() {
        assertTrue(presenter.reserveApartment(ID, CLIENT_NAME));
        verify(hotelService, times(1)).reserveApartment(ID, CLIENT_NAME);
    }

    @Test
    void reserveApartment_ApartmentNotFoundException() {
        doThrow(new ApartmentNotFoundException("Apartment not found."))
                .when(hotelService).reserveApartment(ID, CLIENT_NAME);

        assertFalse(presenter.reserveApartment(ID, CLIENT_NAME));
        verify(view, times(1)).displayError("Apartment not found.");
    }

    @Test
    void reserveApartment_ApartmentAlreadyReservedException() {
        doThrow(new ApartmentAlreadyReservedException("Apartment is already reserved."))
                .when(hotelService).reserveApartment(ID, CLIENT_NAME);

        assertFalse(presenter.reserveApartment(ID, CLIENT_NAME));
        verify(view, times(1)).displayError("Apartment is already reserved.");
    }

    @Test
    void reserveApartment_InvalidNameException() {
        String clientName = "";

        doThrow(new InvalidNameException("Invalid name."))
                .when(hotelService).reserveApartment(ID, clientName);

        assertFalse(presenter.reserveApartment(ID, clientName));
        verify(view, times(1)).displayError("Invalid name.");
    }

    @Test
    void releaseApartment_Success() {
        assertTrue(presenter.releaseApartment(ID));
        verify(hotelService, times(1)).releaseApartment(ID);
    }

    @Test
    void releaseApartment_ApartmentNotReservedException() {
        doThrow(new ApartmentNotReservedException("Apartment is not reserved."))
                .when(hotelService).releaseApartment(ID);

        assertFalse(presenter.releaseApartment(ID));
        verify(view, times(1)).displayError("Apartment is not reserved.");
    }

    @Test
    void listApartments_Success() {
        int page = 1;
        List<Apartment> apartments = new ArrayList<>();
        apartments.add(new Apartment(1, 100.0));

        when(hotelService.listApartments(page)).thenReturn(apartments);

        List<String> result = presenter.listApartments(page);
        assertEquals(1, result.size());
        verify(hotelService, times(1)).listApartments(page);
    }

    @Test
    void listApartments_ApartmentNotFoundException() {
        int page = 2;

        doThrow(new ApartmentNotFoundException("No apartments found for the requested page number."))
                .when(hotelService).listApartments(page);

        List<String> result = presenter.listApartments(page);
        assertTrue(result.isEmpty());
        verify(view, times(1)).
                displayError("No apartments found for the requested page number.");
    }

    @Test
    void getTotalPages() {
        when(hotelService.getTotalPages()).thenReturn(5);

        int totalPages = presenter.getTotalPages();
        assertEquals(5, totalPages);
        verify(hotelService, times(1)).getTotalPages();
    }
}
