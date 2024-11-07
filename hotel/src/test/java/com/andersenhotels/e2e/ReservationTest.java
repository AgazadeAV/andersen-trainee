package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        // Initialize the output stream to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect standard output to capture output

        // Simulate user input for registering an apartment, reserving it, and then exiting
        String simulatedInput = "1\n150.0\n2\n1\nAzer Agazade\n5\n"; // Register and reserve
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Set input stream for the console UI

        consoleUI = new ConsoleUI(); // Initialize the console UI
    }

    @Test
    public void testReserveApartment() {
        // Start the console UI which processes the mocked input
        consoleUI.startWork();

        // Capture the output from the console
        String output = outputStream.toString();

        // Verify that the output indicates successful reservation of the apartment
        assertTrue(output.contains("Apartment reserved successfully."),
                "The output should confirm that the apartment was reserved successfully.");
    }
}
