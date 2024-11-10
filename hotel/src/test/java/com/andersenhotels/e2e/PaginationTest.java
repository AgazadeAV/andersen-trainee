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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaginationTest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        StateManager.setPATH("src/main/resources/hotel_service_state_test.json");
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String simulatedInput = "1\n100.0\n1\n200.0\n1\n300.0\n1\n400.0\n1\n500.0\n1\n600.0\n4\n1\n4\n2\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        consoleUI = new ConsoleUI();
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(StateManager.getPATH()));
    }

    @Test
    public void pagination() {
        consoleUI.startWork();

        String output = outputStream.toString();
        List<String> expectedMessages = List.of(
                "Apartment ID: 1, Price: 100.0, Status: AVAILABLE.",
                "Apartment ID: 2, Price: 200.0, Status: AVAILABLE.",
                "Apartment ID: 3, Price: 300.0, Status: AVAILABLE.",
                "Apartment ID: 4, Price: 400.0, Status: AVAILABLE.",
                "Apartment ID: 5, Price: 500.0, Status: AVAILABLE.",
                "Apartment ID: 6, Price: 600.0, Status: AVAILABLE."
        );

        boolean allMessagesPresent = expectedMessages.stream()
                .allMatch(output::contains);

        assertTrue(allMessagesPresent, "All expected apartment details should be present in the output.");
    }
}
