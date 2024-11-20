package com.andersenhotels.view.common.buttons;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.View;
import com.andersenhotels.view.console_ui.buttons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ButtonsTest {

    @Mock
    private View view;

    private RegisterApartment registerButton;
    private ReserveApartment reserveButton;
    private ReleaseApartment releaseButton;
    private ListApartments listButton;
    private Exit exitButton;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerButton = new RegisterApartment(view);
        reserveButton = new ReserveApartment(view);
        releaseButton = new ReleaseApartment(view);
        listButton = new ListApartments(view);
        exitButton = new Exit(view);
    }

    @Test
    void registerApartment_Success() {
        when(view.registerApartment()).thenReturn(true);

        registerButton.execute();

        verify(view, times(1)).displayMessage("Apartment registered successfully.");
    }

    @Test
    void reserveApartment_Success() {
        when(view.reserveApartment()).thenReturn(true);

        reserveButton.execute();

        verify(view, times(1)).displayMessage("Apartment reserved successfully.");
    }

    @Test
    void releaseApartment_Success() {
        when(view.releaseApartment()).thenReturn(true);

        releaseButton.execute();

        verify(view, times(1)).displayMessage("Apartment released successfully.");
    }

    @Test
    void listApartments_Success() {
        when(view.listApartments()).thenReturn(List.of("Apartment 1", "Apartment 2"));

        listButton.execute();

        verify(view, times(1)).displayMessage("Apartment 1");
        verify(view, times(1)).displayMessage("Apartment 2");
    }

    @Test
    void listApartments_ApartmentNotFoundException() {
        doThrow(new ApartmentNotFoundException("No apartments available")).when(view).listApartments();

        listButton.execute();

        verify(view, times(1)).displayError("No apartments available");
    }

    @Test
    void exit() {
        exitButton.execute();

        verify(view, times(1)).complete();
    }
}
