package com.andersenhotels.view.common;

import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.buttons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuHandlerTest {

    private MenuHandler menuHandler;

    @BeforeEach
    void setUp() {
        View view = mock(View.class);
        menuHandler = new MenuHandler(view);
    }

    @Test
    void getMenu() {
        String expectedMenu = """
                Menu:
                1. Register Apartment
                2. Reserve Apartment
                3. Release Apartment
                4. List Apartments
                5. Exit
                """;

        String actualMenu = menuHandler.getMenu();
        assertEquals(expectedMenu, actualMenu);
    }

    @Test
    void execute_ValidChoice() {
        Button button = mock(RegisterApartment.class);
        menuHandler.getButtons().set(0, button);

        menuHandler.execute(1);

        verify(button, times(1)).execute();
    }

    @Test
    void execute_InvalidChoice() {
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(999));
    }

    @Test
    void getMenuSize() {
        int size = menuHandler.getMenuSize();
        assertEquals(5, size);
    }
}
