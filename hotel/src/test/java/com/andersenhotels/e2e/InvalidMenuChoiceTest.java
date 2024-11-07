package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidMenuChoiceTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    public void setUp() {
        // Initialize the error output stream to capture console error messages
        errorStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorStream)); // Redirect standard error to the ByteArrayOutputStream

        // Simulate user input for an invalid menu choice followed by a valid choice to exit
        String simulatedInput = "9\n5\n"; // '9' is assumed to be an invalid choice, '5' is the exit choice
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Set input stream for the console UI

        consoleUI = new ConsoleUI(); // Initialize the console UI
    }

    @Test
    public void testInvalidMenuChoice() {
        // Start the console UI which processes the mocked input
        consoleUI.startWork();

        // Capture the error output and verify that it contains the expected error message for invalid choice
        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Invalid menu option entered. Please enter a valid number from the menu:"),
                "The application should provide a clear error message for invalid menu choices.");
    }
}
