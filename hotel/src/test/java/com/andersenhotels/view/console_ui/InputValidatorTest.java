package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.common.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputValidatorTest {

    @Mock
    private View view; // Mocked View for displaying messages and errors

    private InputValidator inputValidator; // Instance of InputValidator to be tested

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputValidator = new InputValidator(view); // Initialize InputValidator with mocked View
    }

    @Test
    void testGetIntInput_Valid() {
        // Mock Scanner to simulate valid integer input
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("10");

        // Inject the mock Scanner into InputValidator for testing
        inputValidator = new InputValidator(view, mockScanner);

        // Call method and verify the returned integer value is correct
        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(10, result);
    }

    @Test
    void testGetIntInput_InvalidThenValid() {
        // Mock Scanner to simulate invalid input first, then valid integer input
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("abc", "10");

        // Inject mock Scanner into InputValidator
        inputValidator = new InputValidator(view, mockScanner);

        // Call method and check that the valid integer is returned after an error
        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(10, result);

        // Verify that displayError was called once for the invalid input
        verify(view, times(1)).displayError("Invalid integer value. Please try again.");
    }

    @Test
    void testGetDoubleInput_Valid() {
        // Mock Scanner to simulate valid double input
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("10.5");

        // Inject mock Scanner into InputValidator
        inputValidator = new InputValidator(view, mockScanner);

        // Call method and verify the returned double value is correct
        double result = inputValidator.getDoubleInput("Enter a double:");
        assertEquals(10.5, result);
    }

    @Test
    void testGetDoubleInput_InvalidThenValid() {
        // Mock Scanner to simulate invalid input first, then valid double input
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("abc", "10.5");

        // Inject mock Scanner into InputValidator
        inputValidator = new InputValidator(view, mockScanner);

        // Call method and check that the valid double is returned after an error
        double result = inputValidator.getDoubleInput("Enter a double:");
        assertEquals(10.5, result);

        // Verify that displayError was called once for the invalid input
        verify(view, times(1)).displayError("Invalid number. Please enter a valid double or integer value.");
    }

    @Test
    void testGetStringInput() {
        // Mock Scanner to simulate user entering a string
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("test string");

        // Inject mock Scanner into InputValidator
        inputValidator = new InputValidator(view, mockScanner);

        // Call method and verify the returned string value is correct
        String result = inputValidator.getStringInput("Enter a string:");
        assertEquals("test string", result);
    }
}
