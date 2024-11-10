package com.andersenhotels.integration;

import com.andersenhotels.model.service.StateManager;
import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTerminationIntegrationTest {

    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        StateManager.setPATH("src/main/resources/hotel_service_state_test.json");
        consoleUI = new ConsoleUI();
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(StateManager.getPATH()));
    }

    @Test
    void applicationTermination() {
        consoleUI.finishWork();

        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
