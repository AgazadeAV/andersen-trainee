package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DuplicateReservationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    public void setUp() {
        // Initialize output streams to capture console output and error messages
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect standard output
        System.setErr(new PrintStream(errorStream)); // Redirect error output

        // Simulate user input for registering and reserving apartments, followed by a duplicate reservation
        String simulatedInput = "1\n150.0\n2\n1\nAzer Agazade\n2\n1\nAnar Agazade\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Set input stream for the console UI

        consoleUI = new ConsoleUI(); // Initialize the console UI
    }

    @Test
    public void testDuplicateReservation() {
        // Start the console UI, which processes the mocked input
        consoleUI.startWork();

        // Capture the error output and verify it contains the expected error message for duplicate reservation
        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Apartment is already reserved."),
                "Expected error message for duplicate reservation should be displayed.");
    }
}
