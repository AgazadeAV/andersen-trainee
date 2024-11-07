package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExitTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        // Initialize the output stream to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Redirect standard output to the ByteArrayOutputStream

        // Simulate user input for the exit option in the menu
        String simulatedInput = "5\n"; // Assuming '5' is the option to exit
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Set input stream for the console UI

        consoleUI = new ConsoleUI(); // Initialize the console UI
    }

    @Test
    public void testExitApplication() {
        // Start the console UI which processes the mocked input
        consoleUI.startWork();

        // Capture the output and verify that it contains the exit message
        String output = outputStream.toString();
        assertTrue(output.contains("Good bye!"), "The application should display a farewell message on exit.");
    }
}
