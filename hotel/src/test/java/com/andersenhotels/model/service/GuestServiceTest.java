package com.andersenhotels.model.service;

import com.andersenhotels.model.Guest;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private Guest mockGuest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guestService = spy(new GuestService());
    }

    @Test
    void registerGuest_Success() {
        String name = "John Doe";
        Guest guest = new Guest(name);

        doReturn(guest).when(guestService).create(any(Guest.class));

        Guest result = assertDoesNotThrow(() -> guestService.registerGuest(name));
        assertEquals(guest, result);
        verify(guestService).create(any(Guest.class));
    }

    @Test
    void registerGuest_InvalidName() {
        String invalidName = "";

        assertThrows(InvalidNameException.class, () -> guestService.registerGuest(invalidName));
        verify(guestService, never()).create(any(Guest.class));
    }

    @Test
    void registerGuest_PersistenceException() {
        String name = "John Doe";

        doThrow(PersistenceException.class).when(guestService).create(any(Guest.class));

        assertThrows(PersistenceException.class, () -> guestService.registerGuest(name));
        verify(guestService).create(any(Guest.class));
    }

    @Test
    void deleteGuest_Success() {
        int guestId = 1;
        when(mockGuest.getId()).thenReturn(guestId);
        doReturn(true).when(guestService).existsById(guestId);
        doNothing().when(guestService).delete(guestId);

        assertDoesNotThrow(() -> guestService.deleteGuest(mockGuest));
        verify(guestService).delete(guestId);
    }

    @Test
    void deleteGuest_NotFound() {
        int guestId = 1;
        when(mockGuest.getId()).thenReturn(guestId);
        doReturn(false).when(guestService).existsById(guestId);

        assertThrows(GuestNotFoundException.class, () -> guestService.deleteGuest(mockGuest));
        verify(guestService, never()).delete(anyInt());
    }

    @Test
    void deleteGuest_NullGuest() {
        assertThrows(GuestNotFoundException.class, () -> guestService.deleteGuest(null));
        verify(guestService, never()).delete(anyInt());
    }

    @Test
    void deleteGuest_PersistenceException() {
        int guestId = 1;
        when(mockGuest.getId()).thenReturn(guestId);
        doReturn(true).when(guestService).existsById(guestId);
        doThrow(PersistenceException.class).when(guestService).delete(guestId);

        assertThrows(PersistenceException.class, () -> guestService.deleteGuest(mockGuest));
        verify(guestService).delete(guestId);
    }
}
