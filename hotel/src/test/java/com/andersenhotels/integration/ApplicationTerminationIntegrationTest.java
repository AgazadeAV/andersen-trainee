package com.andersenhotels.integration;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTerminationIntegrationTest {

    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        consoleUI = new ConsoleUI();
    }

    @Test
    void applicationTermination() {
        consoleUI.finishWork();

        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
