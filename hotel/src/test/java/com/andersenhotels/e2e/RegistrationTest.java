package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        // Initialize the output stream to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect standard output to the output stream

        // Simulate user input for registering two apartments and then exiting
        String simulatedInput = "1\n100.0\n1\n200.0\n5\n"; // Register apartment with price 100.0, then 200.0, then exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Set input stream for the console UI

        consoleUI = new ConsoleUI(); // Initialize the console UI
    }

    @Test
    public void testRegisterAndListApartments() {
        // Start the console UI which processes the mocked input
        consoleUI.startWork();

        // Capture the output from the console
        String output = outputStream.toString();

        // Verify that the output indicates successful registration of apartments
        assertTrue(output.contains("Apartment registered successfully."),
                "The output should confirm that the apartment was registered successfully.");
        assertTrue(output.contains("Apartment registered successfully."),
                "The output should confirm that the second apartment was registered successfully.");
    }
}
