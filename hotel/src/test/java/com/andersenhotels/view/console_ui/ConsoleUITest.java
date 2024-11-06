package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.view.common.MenuHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleUITest {

    @Mock
    private MenuHandler menuHandler; // Mocked MenuHandler for simulating menu interactions

    @Mock
    private Presenter presenter; // Mocked Presenter for controlling business logic calls

    @Mock
    private InputValidator inputValidator; // Mocked InputValidator to simulate user input

    @InjectMocks
    private ConsoleUI consoleUI; // ConsoleUI instance under test with dependencies injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleUI.setRunning(false); // Prevent infinite loop by setting isRunning to false
    }

    @Test
    void testRegisterApartment_Success() {
        double price = 150.0;

        // Simulate user input and successful registration
        when(inputValidator.getDoubleInput(anyString())).thenReturn(price);
        when(presenter.registerApartment(price)).thenReturn(true);

        // Verify that registration succeeds
        assertTrue(consoleUI.registerApartment());
        verify(presenter, times(1)).registerApartment(price);
    }

    @Test
    void testRegisterApartment_Failure() {
        double price = 150.0;

        // Simulate user input and unsuccessful registration
        when(inputValidator.getDoubleInput(anyString())).thenReturn(price);
        when(presenter.registerApartment(price)).thenReturn(false);

        // Verify that registration fails
        assertFalse(consoleUI.registerApartment());
        verify(presenter, times(1)).registerApartment(price);
    }

    @Test
    void testReserveApartment_Success() {
        int id = 1;
        String guestName = "Azer Agazade";

        // Simulate user input and successful reservation
        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(inputValidator.getStringInput(anyString())).thenReturn(guestName);
        when(presenter.reserveApartment(id, guestName)).thenReturn(true);

        // Verify that reservation succeeds
        assertTrue(consoleUI.reserveApartment());
        verify(presenter, times(1)).reserveApartment(id, guestName);
    }

    @Test
    void testReserveApartment_Failure() {
        int id = 1;
        String guestName = "Azer Agazade";

        // Simulate user input and unsuccessful reservation
        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(inputValidator.getStringInput(anyString())).thenReturn(guestName);
        when(presenter.reserveApartment(id, guestName)).thenReturn(false);

        // Verify that reservation fails
        assertFalse(consoleUI.reserveApartment());
        verify(presenter, times(1)).reserveApartment(id, guestName);
    }

    @Test
    void testReleaseApartment_Success() {
        int id = 1;

        // Simulate user input and successful release
        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(presenter.releaseApartment(id)).thenReturn(true);

        // Verify that release succeeds
        assertTrue(consoleUI.releaseApartment());
        verify(presenter, times(1)).releaseApartment(id);
    }

    @Test
    void testReleaseApartment_Failure() {
        int id = 1;

        // Simulate user input and unsuccessful release
        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(presenter.releaseApartment(id)).thenReturn(false);

        // Verify that release fails
        assertFalse(consoleUI.releaseApartment());
        verify(presenter, times(1)).releaseApartment(id);
    }

    @Test
    void testListApartments() {
        int page = 1;
        List<String> apartments = Arrays.asList("Apartment 1", "Apartment 2");

        // Set up total pages and valid input to avoid ApartmentNotFoundException
        when(presenter.getTotalPages()).thenReturn(1);
        when(inputValidator.getIntInput(anyString())).thenReturn(page);
        when(presenter.listApartments(page)).thenReturn(apartments);

        // Verify that listApartments returns expected list
        List<String> result = consoleUI.listApartments();
        assertEquals(apartments, result);
        verify(presenter, times(1)).listApartments(page);
    }

    @Test
    void testFinishWork() {
        // Call finishWork to stop the application
        consoleUI.finishWork();

        // Verify that isRunning is set to false
        assertFalse(consoleUI.isRunning());
    }
}
