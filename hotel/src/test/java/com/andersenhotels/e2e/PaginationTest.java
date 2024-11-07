package com.andersenhotels.e2e;

import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String simulatedInput = "1\n100.0\n1\n200.0\n1\n300.0\n1\n400.0\n1\n500.0\n1\n600.0\n4\n1\n4\n2\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
    }

    @Test
    public void pagination() {
        consoleUI.startWork();

        String output = outputStream.toString();

        assertTrue(output.contains("Apartment ID: 1, Price: 100.0, Status: AVAILABLE."),
                "Expected apartment details should be printed for the first page.");
        assertTrue(output.contains("Apartment ID: 2, Price: 200.0, Status: AVAILABLE."));
        assertTrue(output.contains("Apartment ID: 3, Price: 300.0, Status: AVAILABLE."));
        assertTrue(output.contains("Apartment ID: 4, Price: 400.0, Status: AVAILABLE."));
        assertTrue(output.contains("Apartment ID: 5, Price: 500.0, Status: AVAILABLE."));

        assertTrue(output.contains("Apartment ID: 6, Price: 600.0, Status: AVAILABLE."),
                "Expected apartment details should be printed for the second page.");
    }
}
