package com.andersenhotels.view.common;

import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.view.common.buttons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuHandlerTest {

    private MenuHandler menuHandler; // Instance of MenuHandler for testing

    @BeforeEach
    void setUp() {
        // Mock View instance for initializing MenuHandler
        View view = mock(View.class);
        menuHandler = new MenuHandler(view); // Initialize MenuHandler with mocked View
    }

    @Test
    void testGetMenu() {
        // Expected menu format based on the buttons in MenuHandler
        String expectedMenu = """
                Menu:
                1. Register Apartment
                2. Reserve Apartment
                3. Release Apartment
                4. List Apartments
                5. Exit
                """;

        // Call getMenu and compare with expected format
        String actualMenu = menuHandler.getMenu();
        assertEquals(expectedMenu, actualMenu);
    }

    @Test
    void testExecute_ValidChoice() {
        // Mock a Button instance and set it as the first button in MenuHandler's list
        Button button = mock(RegisterApartment.class);
        menuHandler.getButtons().set(0, button);

        // Execute the first menu option
        menuHandler.execute(1);

        // Verify that the execute method was called on the button
        verify(button, times(1)).execute();
    }

    @Test
    void testExecute_InvalidChoice() {
        // Verify that executing an invalid choice throws the expected exception
        assertThrows(WrongMenuChoiceException.class, () -> menuHandler.execute(999));
    }

    @Test
    void testGetMenuSize() {
        // Verify that the menu size is as expected (5 buttons)
        int size = menuHandler.getMenuSize();
        assertEquals(5, size);
    }
}
