package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Guest;
import com.andersenhotels.model.storage.jpa.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(long id) {
        return guestRepository.findById(id);
    }

    public Guest registerGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public void deleteGuest(long id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Guest not found with ID: " + id);
        }
    }
}