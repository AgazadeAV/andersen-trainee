package com.andersenhotels.integration;

import com.andersenhotels.model.service.StateManager;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuChoiceIntegrationTest {

    private MenuHandler menuHandler;
    private View view;

    @BeforeEach
    public void setUp() {
        StateManager.setPATH("src/main/resources/hotel_service_state_test.json");
        view = new ConsoleUI();
        menuHandler = new MenuHandler(view);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(StateManager.getPATH()));
    }

    @Test
    public void menuChoice_ValidInput() {
        int validChoice = menuHandler.getMenuSize();
        assertDoesNotThrow(() -> menuHandler.execute(validChoice),
                "Menu should accept a valid choice without throwing an exception.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MAX_VALUE})
    public void menuChoice_InvalidInput(int invalidChoice) {
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(invalidChoice),
                "Menu should throw WrongMenuChoiceException for an invalid choice.");
    }
}
