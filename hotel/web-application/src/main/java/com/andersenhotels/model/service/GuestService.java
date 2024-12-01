package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Guest;
import com.andersenhotels.model.storage.jpa.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(long id) {
        return guestRepository.findById(id);
    }

    public Guest registerGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public boolean deleteGuest(long id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
