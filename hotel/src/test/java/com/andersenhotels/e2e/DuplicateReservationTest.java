package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    }

    @Test
    public void duplicateReservation() {
        consoleUI.startWork();

        String errorOutput = errorStream.toString();
        assertTrue(errorOutput.contains("Apartment is already reserved."),
                "Expected error message for duplicate reservation should be displayed.");
    }
}
