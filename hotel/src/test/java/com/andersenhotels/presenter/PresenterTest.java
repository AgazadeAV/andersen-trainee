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
    private HotelService hotelService; // Mocked service for managing hotel operations

    @Mock
    private View view; // Mocked view to simulate user interface interactions

    private Presenter presenter; // Presenter instance to be tested

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new Presenter(view, hotelService); // Initialize Presenter with mocked dependencies
    }

    @Test
    void testRegisterApartment_Success() {
        double price = 100.0;

        // Verify that registration is successful and hotelService's method is called
        assertTrue(presenter.registerApartment(price));
        verify(hotelService, times(1)).registerApartment(price);
    }

    @Test
    void testRegisterApartment_InvalidPrice() {
        double price = -50.0;

        // Simulate an exception for an invalid price
        doThrow(new InvalidPriceException("The price should be a positive number."))
                .when(hotelService).registerApartment(price);

        // Verify that registration fails and the error message is displayed
        assertFalse(presenter.registerApartment(price));
        verify(view, times(1)).displayError("The price should be a positive number.");
    }

    @Test
    void testReserveApartment_Success() {
        int id = 1;
        String clientName = "Azer Agazade";

        // Verify that reservation is successful and the method is called correctly
        assertTrue(presenter.reserveApartment(id, clientName));
        verify(hotelService, times(1)).reserveApartment(id, clientName);
    }

    @Test
    void testReserveApartment_ApartmentNotFoundException() {
        int id = 1;
        String clientName = "Azer Agazade";

        // Simulate exception for apartment not found
        doThrow(new ApartmentNotFoundException("Apartment not found."))
                .when(hotelService).reserveApartment(id, clientName);

        // Verify that reservation fails and the correct error message is shown
        assertFalse(presenter.reserveApartment(id, clientName));
        verify(view, times(1)).displayError("Apartment not found.");
    }

    @Test
    void testReserveApartment_ApartmentAlreadyReservedException() {
        int id = 1;
        String clientName = "Azer Agazade";

        // Simulate exception when the apartment is already reserved
        doThrow(new ApartmentAlreadyReservedException("Apartment is already reserved."))
                .when(hotelService).reserveApartment(id, clientName);

        // Verify that reservation fails and displays the correct error message
        assertFalse(presenter.reserveApartment(id, clientName));
        verify(view, times(1)).displayError("Apartment is already reserved.");
    }

    @Test
    void testReserveApartment_InvalidNameException() {
        int id = 1;
        String clientName = "";

        // Simulate exception for invalid name
        doThrow(new InvalidNameException("Invalid name."))
                .when(hotelService).reserveApartment(id, clientName);

        // Verify that reservation fails and shows the correct error message
        assertFalse(presenter.reserveApartment(id, clientName));
        verify(view, times(1)).displayError("Invalid name.");
    }

    @Test
    void testReleaseApartment_Success() {
        int id = 1;

        // Verify that releasing the apartment succeeds and the correct method is called
        assertTrue(presenter.releaseApartment(id));
        verify(hotelService, times(1)).releaseApartment(id);
    }

    @Test
    void testReleaseApartment_ApartmentNotReservedException() {
        int id = 1;

        // Simulate exception when trying to release a non-reserved apartment
        doThrow(new ApartmentNotReservedException("Apartment is not reserved."))
                .when(hotelService).releaseApartment(id);

        // Verify that releasing fails and the error message is displayed
        assertFalse(presenter.releaseApartment(id));
        verify(view, times(1)).displayError("Apartment is not reserved.");
    }

    @Test
    void testListApartments_Success() {
        int page = 1;
        List<Apartment> apartments = new ArrayList<>();
        apartments.add(new Apartment(1, 100.0));

        // Simulate listing apartments on a specific page
        when(hotelService.listApartments(page)).thenReturn(apartments);

        // Verify that list contains the expected results and calls the correct method
        List<String> result = presenter.listApartments(page);
        assertEquals(1, result.size());
        verify(hotelService, times(1)).listApartments(page);
    }

    @Test
    void testListApartments_ApartmentNotFoundException() {
        int page = 2;

        // Simulate exception when no apartments are found on the specified page
        doThrow(new ApartmentNotFoundException("No apartments found for the requested page number."))
                .when(hotelService).listApartments(page);

        // Verify that the list is empty and displays the correct error message
        List<String> result = presenter.listApartments(page);
        assertTrue(result.isEmpty());
        verify(view, times(1)).displayError("No apartments found for the requested page number.");
    }

    @Test
    void testGetTotalPages() {
        // Simulate retrieving the total number of pages
        when(hotelService.getTotalPages()).thenReturn(5);

        // Verify that the total pages returned is as expected
        int totalPages = presenter.getTotalPages();
        assertEquals(5, totalPages);
        verify(hotelService, times(1)).getTotalPages();
    }
}
