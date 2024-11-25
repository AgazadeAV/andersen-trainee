package com.andersenhotels.model.service;

import com.andersenhotels.model.*;
import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.attribute.HashPrintJobAttributeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Spy
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private Guest guest;

    @Mock
    private Apartment apartment;

    @Test
    void createReservation_ApartmentAlreadyReserved() {
        when(apartment.getId()).thenReturn(1);
        when(apartment.getStatus()).thenReturn(ApartmentStatus.RESERVED);

        assertThrows(ApartmentAlreadyReservedException.class, () -> {
            reservationService.createReservation(apartment, guest);
        });

        verify(reservationService, never()).updateApartmentStatus(any(), any());
    }

    @Test
    void cancelReservation_NotFound() {
        Reservation reservation = mock(Reservation.class);
        when(reservation.getId()).thenReturn(1);
        doReturn(false).when(reservationService).existsById(1);

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.cancelReservation(reservation);
        });

        verify(reservationService, never()).delete(anyInt());
        verify(apartmentService, never()).update(any());
    }

    @Test
    void updateApartmentStatus_InvalidApartment() {
        Apartment invalidApartment = null;

        assertThrows(NullPointerException.class, () -> {
            reservationService.updateApartmentStatus(invalidApartment, ApartmentStatus.RESERVED);
        });

        verify(apartmentService, never()).update(any());
    }
}
