package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestTest {

    @Test
    void constructor_ThrowsInvalidNameExceptionForNull() {
        Exception exception = assertThrows(InvalidNameException.class, () -> new Guest(null));
        assertEquals("Guest name cannot be null, empty, only whitespace or start with a number.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsInvalidNameExceptionForEmptyName() {
        Exception exception = assertThrows(InvalidNameException.class, () -> new Guest("   "));
        assertEquals("Guest name cannot be null, empty, only whitespace or start with a number.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsInvalidNameExceptionForNameStartingWithDigit() {
        Exception exception = assertThrows(InvalidNameException.class, () -> new Guest("123John"));
        assertEquals("Guest name cannot be null, empty, only whitespace or start with a number.", exception.getMessage());
    }

    @Test
    void constructor_ValidName() {
        Guest guest = new Guest("   John Doe   ");
        assertEquals("John Doe", guest.getName());
    }
}
