package com.andersenhotels.model.service;

import com.andersenhotels.model.Guest;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Spy
    @InjectMocks
    private GuestService guestService;

    @Mock
    private Guest mockGuest;

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

        doThrow(new InvalidNameException("Name cannot be empty")).when(guestService).registerGuest(invalidName);

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
