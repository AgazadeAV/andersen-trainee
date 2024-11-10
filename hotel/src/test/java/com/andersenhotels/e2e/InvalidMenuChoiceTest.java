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

public class InvalidMenuChoiceTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream errorStream;

    @BeforeEach
    public void setUp() {
        errorStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorStream));

        String simulatedInput = "9\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
        consoleUI.setTesting(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(ConsoleUI.getTEST_PATH()));
    }

    @Test
    public void invalidMenuChoice() {
        consoleUI.startWork();

        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Invalid menu option entered. Please enter a valid number from the menu:"),
                "The application should provide a clear error message for invalid menu choices.");
    }
}
