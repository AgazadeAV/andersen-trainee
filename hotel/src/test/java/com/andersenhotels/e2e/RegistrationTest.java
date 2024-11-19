package com.andersenhotels.e2e;

import com.andersenhotels.model.storage.json_storage.JsonStorage;
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

public class RegistrationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String simulatedInput = "1\n1\n100.0\n1\n200.0\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
        consoleUI.setTesting(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(JsonStorage.getTEST_PATH()));
    }

    @Test
    public void registerAndListApartments() {
        consoleUI.initialize();

        String output = outputStream.toString();
        String expectedMessage = "Apartment registered successfully.";

        long count = output.lines()
                .filter(line -> line.contains(expectedMessage))
                .count();

        assertTrue(count == 2, "The output should confirm that both apartments were registered successfully.");
    }
}
