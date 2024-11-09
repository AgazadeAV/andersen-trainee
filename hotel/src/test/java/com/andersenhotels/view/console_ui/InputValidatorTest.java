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
    private View view;

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputValidator = new InputValidator(view);
    }

    @Test
    void getIntInput_Valid() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("10");

        inputValidator = new InputValidator(view, mockScanner);

        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(10, result);
    }

    @Test
    void getIntInput_InvalidThenValid() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("abc", "10");

        inputValidator = new InputValidator(view, mockScanner);

        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(10, result);

        verify(view, times(1)).
                displayError("Invalid integer value. Please try again.");
    }

    @Test
    void getDoubleInput_Valid() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("10.5");

        inputValidator = new InputValidator(view, mockScanner);

        double result = inputValidator.getDoubleInput("Enter a double:");
        assertEquals(10.5, result);
    }

    @Test
    void getDoubleInput_InvalidThenValid() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("abc", "10.5");

        inputValidator = new InputValidator(view, mockScanner);

        double result = inputValidator.getDoubleInput("Enter a double:");
        assertEquals(10.5, result);

        verify(view, times(1)).
                displayError("Invalid number. Please enter a valid double or integer value.");
    }

    @Test
    void getStringInput() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("test string");

        inputValidator = new InputValidator(view, mockScanner);

        String result = inputValidator.getStringInput("Enter a string:");
        assertEquals("test string", result);
    }
}
