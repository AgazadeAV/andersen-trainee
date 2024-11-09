package com.andersenhotels.integration;

import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;
import com.andersenhotels.view.console_ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void menuChoice_InvalidInput_MoreThanMenuSize() {
        int invalidChoice = menuHandler.getMenuSize() + 1;
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(invalidChoice),
                "Menu should throw WrongMenuChoiceException for an invalid choice.");
    }

    @Test
    public void menuChoice_InvalidInput_NegativeValue() {
        int invalidChoice = -1;
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(invalidChoice),
                "Menu should throw WrongMenuChoiceException for an invalid choice.");
    }

    @Test
    public void menuChoice_InvalidInput_ZeroValue() {
        int invalidChoice = 0;
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(invalidChoice),
                "Menu should throw WrongMenuChoiceException for an invalid choice.");
    }
}
