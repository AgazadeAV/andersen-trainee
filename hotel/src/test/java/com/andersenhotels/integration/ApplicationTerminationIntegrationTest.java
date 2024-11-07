package com.andersenhotels.integration;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTerminationIntegrationTest {

    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        // Initialize a new ConsoleUI instance before each test
        consoleUI = new ConsoleUI();
    }

    @Test
    void testApplicationTermination() {
        // Test application termination by calling the finishWork method
        consoleUI.finishWork();

        // Verify that the isRunning method returns false, indicating the application has terminated
        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
