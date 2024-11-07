package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReleaseApartmentTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String simulatedInput = "1\n200.0\n2\n1\nAzer Agazade\n3\n1\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
    }

    @Test
    public void releaseApartment() {
        consoleUI.startWork();

        String output = outputStream.toString();

        assertTrue(output.contains("Apartment released successfully."),
                "The output should confirm that the apartment was released successfully.");
    }
}
