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
    private InputValidator inputValidator;

    @InjectMocks
    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleUI.setInputValidator(inputValidator);
        consoleUI.setRunning(true);
    }

    @Test
    void registerApartment() {
        when(inputValidator.getDoubleInput(anyString())).thenReturn(100.0);
        when(presenter.registerApartment(anyDouble())).thenReturn(true);

        boolean result = consoleUI.registerApartment();

        assertTrue(result, "The apartment should be registered successfully.");
        verify(presenter, times(1)).registerApartment(100.0);
    }

    @Test
    void reserveApartment() {
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(inputValidator.getStringInput(anyString())).thenReturn("Azer Agazade");
        when(presenter.reserveApartment(anyInt(), anyString())).thenReturn(true);

        boolean result = consoleUI.reserveApartment();

        assertTrue(result, "The apartment should be reserved successfully.");
        verify(presenter, times(1)).reserveApartment(1, "Azer Agazade");
    }

    @Test
    void releaseApartment() {
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(presenter.releaseApartment(anyInt())).thenReturn(true);

        boolean result = consoleUI.releaseApartment();

        assertTrue(result, "The apartment should be released successfully.");
        verify(presenter, times(1)).releaseApartment(1);
    }

    @Test
    void listApartments() {
        when(presenter.getTotalPages()).thenReturn(1);
        when(inputValidator.getIntInput(anyString())).thenReturn(1);
        when(presenter.listApartments(anyInt())).thenReturn(List.of("Apartment 1", "Apartment 2"));

        List<String> apartments = consoleUI.listApartments();

        assertNotNull(apartments, "The apartment list should not be null.");
        assertEquals(2, apartments.size(), "There should be two apartments in the list.");
        verify(presenter, times(1)).listApartments(1);
    }

    @Test
    void finishWork() {
        consoleUI.finishWork();

        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
