package com.andersenhotels.integration;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.view.console_ui.ConsoleUI;
import com.andersenhotels.view.console_ui.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ConsoleUIPresenterIntegrationTest {

    @Mock
    private Presenter presenter;

    @Mock
    private InputValidator inputValidator; // Mocking the InputValidator

    @InjectMocks
    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them into ConsoleUI before each test
        MockitoAnnotations.openMocks(this);
        consoleUI.setInputValidator(inputValidator); // Set the mocked InputValidator in ConsoleUI
        consoleUI.setRunning(true); // Ensure the application is in a running state for tests
    }

    @Test
    void testRegisterApartment() {
        // Mock user input and Presenter response for apartment registration
        when(inputValidator.getDoubleInput(anyString())).thenReturn(100.0);
        when(presenter.registerApartment(anyDouble())).thenReturn(true);

        // Call the method and assert that the apartment registration succeeds
        boolean result = consoleUI.registerApartment();

        assertTrue(result, "The apartment should be registered successfully.");
        // Verify that presenter.registerApartment was called once with the value 100.0
        verify(presenter, times(1)).registerApartment(100.0);
    }

    @Test
    void testReserveApartment() {
        // Mock user input and Presenter response for apartment reservation
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(inputValidator.getStringInput(anyString())).thenReturn("John Doe");
        when(presenter.reserveApartment(anyInt(), anyString())).thenReturn(true);

        // Call the method and assert that the reservation succeeds
        boolean result = consoleUI.reserveApartment();

        assertTrue(result, "The apartment should be reserved successfully.");
        // Verify that presenter.reserveApartment was called once with ID 1 and name "John Doe"
        verify(presenter, times(1)).reserveApartment(1, "John Doe");
    }

    @Test
    void testReleaseApartment() {
        // Mock user input and Presenter response for apartment release
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(presenter.releaseApartment(anyInt())).thenReturn(true);

        // Call the method and assert that the release operation succeeds
        boolean result = consoleUI.releaseApartment();

        assertTrue(result, "The apartment should be released successfully.");
        // Verify that presenter.releaseApartment was called once with ID 1
        verify(presenter, times(1)).releaseApartment(1);
    }

    @Test
    void testListApartments() {
        // Mock total pages, user input for page number, and Presenter response for listing apartments
        when(presenter.getTotalPages()).thenReturn(1);
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(presenter.listApartments(anyInt())).thenReturn(List.of("Apartment 1", "Apartment 2"));

        // Call the method and assert that it returns a list of apartments
        List<String> apartments = consoleUI.listApartments();

        assertNotNull(apartments, "The apartment list should not be null.");
        assertEquals(2, apartments.size(), "There should be two apartments in the list.");
        // Verify that presenter.listApartments was called once for page 1
        verify(presenter, times(1)).listApartments(1);
    }

    @Test
    void testFinishWork() {
        // Call the method to finish work and verify that the application state changes to terminated
        consoleUI.finishWork();

        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
