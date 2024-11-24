package com.andersenhotels.model.service;

import com.andersenhotels.model.*;
import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private Guest guest;

    @Mock
    private Apartment apartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = spy(new ReservationService() {
            @Override
            void updateApartmentStatus(Apartment apartment, ApartmentStatus status) {
                apartment.setStatus(status);
                apartmentService.update(apartment);
            }
        });
    }

    @Test
    void createReservation_Success() throws Exception {
        when(apartment.getId()).thenReturn(1);
        when(guest.getId()).thenReturn(1);
        when(apartment.getStatus()).thenReturn(ApartmentStatus.AVAILABLE);
        doReturn(new Reservation(apartment, guest)).when(reservationService).create(any(Reservation.class));

        Reservation reservation = reservationService.createReservation(apartment, guest);

        assertNotNull(reservation);
        verify(reservationService, times(1)).updateApartmentStatus(apartment, ApartmentStatus.RESERVED);
        verify(apartmentService, times(1)).update(apartment);
    }

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
    void cancelReservation_Success() throws Exception {
        Reservation reservation = mock(Reservation.class);
        when(reservation.getId()).thenReturn(1);
        doReturn(true).when(reservationService).existsById(1);
        when(reservation.getApartment()).thenReturn(apartment);

        reservationService.cancelReservation(reservation);

        verify(reservationService, times(1)).delete(1);
        verify(reservationService, times(1)).updateApartmentStatus(apartment, ApartmentStatus.AVAILABLE);
        verify(apartmentService, times(1)).update(apartment);
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
    void updateApartmentStatus_Success() {
        when(apartment.getId()).thenReturn(1);

        assertDoesNotThrow(() -> reservationService.updateApartmentStatus(apartment, ApartmentStatus.RESERVED));

        verify(apartment, times(1)).setStatus(ApartmentStatus.RESERVED);
        verify(apartmentService, times(1)).update(apartment);
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
