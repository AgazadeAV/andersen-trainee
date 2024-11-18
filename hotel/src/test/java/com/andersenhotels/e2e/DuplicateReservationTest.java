package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DuplicateReservationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));

        String simulatedInput = "1\n150.0\n2\n1\nAzer Agazade\n2\n1\nAnar Agazade\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
        consoleUI.setTesting(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(ConsoleUI.getTEST_PATH()));
    }

    @Test
    public void duplicateReservation() {
        consoleUI.initialize();

        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Apartment is already reserved."),
                "Expected error message for duplicate reservation should be displayed.");
    }
}
