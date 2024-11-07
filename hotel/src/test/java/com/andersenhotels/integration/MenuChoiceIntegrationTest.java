package com.andersenhotels.integration;

import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class MenuChoiceIntegrationTest {
    private MenuHandler menuHandler;
    private View view;

    @BeforeEach
    public void setUp() {
        view = mock(View.class);
        menuHandler = new MenuHandler(view);
    }

    @Test
    public void validMenuChoice() {
        int validChoice = 1;
        assertDoesNotThrow(() -> menuHandler.execute(validChoice),
                "Menu should accept a valid choice without throwing an exception.");
    }

    @Test
    public void invalidMenuChoice() {
        int invalidChoice = menuHandler.getMenuSize() + 1;
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(invalidChoice),
                "Menu should throw WrongMenuChoiceException for an invalid choice.");
    }
}
