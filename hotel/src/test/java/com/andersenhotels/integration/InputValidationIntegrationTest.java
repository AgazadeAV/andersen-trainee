package com.andersenhotels.integration;

import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;
import com.andersenhotels.view.console_ui.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationIntegrationTest {

    private InputValidator inputValidator;
    private View view;

    @BeforeEach
    public void setUp() {
        view = new ConsoleUI();
    }

    @Test
    public void getIntInput_ValidData() {
        String data = "42\n";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        inputValidator = new InputValidator(view, new Scanner(inputStream));

        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(42, result, "The input should be parsed as integer 42.");
    }

    @Test
    public void getIntInput_InvalidData() {
        String data = "abc\n42\n";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        inputValidator = new InputValidator(view, new Scanner(inputStream));

        int result = inputValidator.getIntInput("Enter an integer:");
        assertEquals(42, result,
                "After invalid input, the input should be parsed as integer 42.");
    }
}
