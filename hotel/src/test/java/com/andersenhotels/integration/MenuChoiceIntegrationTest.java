package com.andersenhotels.integration;

import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuChoiceIntegrationTest {
    private MenuHandler menuHandler;
    private View view;

    @BeforeEach
    public void setUp() {
        view = new ConsoleUI();
        menuHandler = new MenuHandler(view);
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
