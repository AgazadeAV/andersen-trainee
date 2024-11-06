package com.andersenhotels.view.common.buttons;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ButtonsTest {

    @Mock
    private View view; // Mocked View to simulate interactions for testing buttons

    // Instances of buttons under test
    private RegisterApartment registerButton;
    private ReserveApartment reserveButton;
    private ReleaseApartment releaseButton;
    private ListApartments listButton;
    private Exit exitButton;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        // Initialize button instances with the mocked view
        registerButton = new RegisterApartment(view);
        reserveButton = new ReserveApartment(view);
        releaseButton = new ReleaseApartment(view);
        listButton = new ListApartments(view);
        exitButton = new Exit(view);
    }

    @Test
    void testRegisterApartment_Success() {
        // Simulate successful apartment registration
        when(view.registerApartment()).thenReturn(true);

        // Execute the button's action
        registerButton.execute();

        // Verify that success message is displayed
        verify(view, times(1)).displayMessage("Apartment registered successfully.");
    }

    @Test
    void testReserveApartment_Success() {
        // Simulate successful apartment reservation
        when(view.reserveApartment()).thenReturn(true);

        // Execute the button's action
        reserveButton.execute();

        // Verify that success message is displayed
        verify(view, times(1)).displayMessage("Apartment reserved successfully.");
    }

    @Test
    void testReleaseApartment_Success() {
        // Simulate successful apartment release
        when(view.releaseApartment()).thenReturn(true);

        // Execute the button's action
        releaseButton.execute();

        // Verify that success message is displayed
        verify(view, times(1)).displayMessage("Apartment released successfully.");
    }

    @Test
    void testListApartments_Success() {
        // Simulate successful listing of apartments
        when(view.listApartments()).thenReturn(List.of("Apartment 1", "Apartment 2"));

        // Execute the button's action
        listButton.execute();

        // Verify that each apartment is displayed
        verify(view, times(1)).displayMessage("Apartment 1");
        verify(view, times(1)).displayMessage("Apartment 2");
    }

    @Test
    void testListApartments_ApartmentNotFoundException() {
        // Simulate exception when no apartments are available
        doThrow(new ApartmentNotFoundException("No apartments available")).when(view).listApartments();

        // Execute the button's action
        listButton.execute();

        // Verify that the error message is displayed
        verify(view, times(1)).displayError("No apartments available");
    }

    @Test
    void testExit() {
        // Execute the exit button's action
        exitButton.execute();

        // Verify that finishWork is called to end the application
        verify(view, times(1)).finishWork();
    }
}
