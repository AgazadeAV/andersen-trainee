package com.andersenhotels.integration;

import com.andersenhotels.model.storage.json_storage.JsonStorage;
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
        consoleUI = new ConsoleUI();
        consoleUI.setTesting(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(JsonStorage.getTEST_PATH()));
    }

    @Test
    void applicationTermination() {
        consoleUI.complete();

        assertFalse(consoleUI.isRunning(), "The application should be terminated.");
    }
}
