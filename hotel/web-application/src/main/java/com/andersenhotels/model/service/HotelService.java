package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Hotel;
import com.andersenhotels.model.storage.jpa.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Hotel not found with ID: " + id);
        }
    }
}
