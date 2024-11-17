package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
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
    private Presenter presenter;

    @Mock
    private InputValidator inputValidator;

    @InjectMocks
    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleUI.setRunning(false);
    }

    @Test
    void registerApartment_Success() {
        double price = 150.0;

        when(inputValidator.getDoubleInput(anyString())).thenReturn(price);
        when(presenter.registerApartment(price)).thenReturn(true);

        assertTrue(consoleUI.registerApartment());
        verify(presenter, times(1)).registerApartment(price);
    }

    @Test
    void registerApartment_Failure() {
        double price = 150.0;

        when(inputValidator.getDoubleInput(anyString())).thenReturn(price);
        when(presenter.registerApartment(price)).thenReturn(false);

        assertFalse(consoleUI.registerApartment());
        verify(presenter, times(1)).registerApartment(price);
    }

    @Test
    void reserveApartment_Success() {
        int id = 1;
        String guestName = "Azer Agazade";

        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(inputValidator.getStringInput(anyString())).thenReturn(guestName);
        when(presenter.reserveApartment(id, guestName)).thenReturn(true);

        assertTrue(consoleUI.reserveApartment());
        verify(presenter, times(1)).reserveApartment(id, guestName);
    }

    @Test
    void reserveApartment_Failure() {
        int id = 1;
        String guestName = "Azer Agazade";

        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(inputValidator.getStringInput(anyString())).thenReturn(guestName);
        when(presenter.reserveApartment(id, guestName)).thenReturn(false);

        assertFalse(consoleUI.reserveApartment());
        verify(presenter, times(1)).reserveApartment(id, guestName);
    }

    @Test
    void releaseApartment_Success() {
        int id = 1;

        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(presenter.releaseApartment(id)).thenReturn(true);

        assertTrue(consoleUI.releaseApartment());
        verify(presenter, times(1)).releaseApartment(id);
    }

    @Test
    void releaseApartment_Failure() {
        int id = 1;

        when(inputValidator.getIntInput(anyString())).thenReturn(id);
        when(presenter.releaseApartment(id)).thenReturn(false);

        assertFalse(consoleUI.releaseApartment());
        verify(presenter, times(1)).releaseApartment(id);
    }

    @Test
    void listApartments() {
        int page = 1;
        List<String> apartments = Arrays.asList("Apartment 1", "Apartment 2");

        when(presenter.getTotalPages()).thenReturn(1);
        when(inputValidator.getIntInput(anyString())).thenReturn(page);
        when(presenter.listApartments(page)).thenReturn(apartments);

        List<String> result = consoleUI.listApartments();
        assertEquals(apartments, result);
        verify(presenter, times(1)).listApartments(page);
    }

    @Test
    void complete() {
        consoleUI.complete();

        assertFalse(consoleUI.isRunning());
    }
}
