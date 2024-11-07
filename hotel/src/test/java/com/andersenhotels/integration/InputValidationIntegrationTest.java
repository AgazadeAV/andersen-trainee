package com.andersenhotels.integration;

import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationIntegrationTest {
    private InputValidator inputValidator;
    private View view;

    @BeforeEach
    public void setUp() {
        // Mock the View interface before each test
        view = Mockito.mock(View.class);
    }

    @Test
    public void testGetIntInput_ValidData() {
        // Simulate valid integer input
        String data = "42\n";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        inputValidator = new InputValidator(view, new Scanner(inputStream));

        // Call getIntInput and verify the correct integer is returned
        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(42, result, "The input should be parsed as integer 42.");
    }

    @Test
    public void testGetIntInput_InvalidData() {
        // Simulate invalid input followed by valid integer input
        String data = "abc\n42\n";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        inputValidator = new InputValidator(view, new Scanner(inputStream));

        // Call getIntInput and verify that the correct integer is returned after invalid input
        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(42, result, "After invalid input, the input should be parsed as integer 42.");

        // Verify that an error message was displayed once due to the initial invalid input
        Mockito.verify(view, Mockito.times(1))
                .displayError("Invalid integer value. Please try again.");
    }
}
