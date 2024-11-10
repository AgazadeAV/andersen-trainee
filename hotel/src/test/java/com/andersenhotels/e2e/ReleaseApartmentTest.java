package com.andersenhotels.e2e;

import com.andersenhotels.model.service.StateManager;
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

public class ReleaseApartmentTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        StateManager.setPATH("src/main/resources/hotel_service_state_test.json");
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String simulatedInput = "1\n200.0\n2\n1\nAzer Agazade\n3\n1\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(StateManager.getPATH()));
    }

    @Test
    public void releaseApartment() {
        consoleUI.startWork();

        String output = outputStream.toString();
        String expectedMessage = "Apartment released successfully.";

        long count = output.lines()
                .filter(line -> line.contains(expectedMessage))
                .count();

        assertTrue(count == 1, "The output should confirm that the apartment was released successfully.");
    }
}
