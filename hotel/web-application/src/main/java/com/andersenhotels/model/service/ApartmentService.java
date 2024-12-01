package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.ApartmentStatus;
import com.andersenhotels.model.storage.jpa.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Optional<Apartment> getApartmentById(long id) {
        return apartmentRepository.findById(id);
    }

    public Apartment registerApartment(Apartment apartment) {
        apartment.setStatus(ApartmentStatus.AVAILABLE);
        return apartmentRepository.save(apartment);
    }

    public Optional<Apartment> updateApartment(long id, Apartment updatedApartment) {
        return apartmentRepository.findById(id)
                .map(apartment -> {
                    apartment.setPrice(updatedApartment.getPrice());
                    apartment.setStatus(updatedApartment.getStatus());
                    return apartmentRepository.save(apartment);
                });
    }

    public boolean deleteApartment(long id) {
        if (apartmentRepository.existsById(id)) {
            apartmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Apartment> getAvailableApartments() {
        return apartmentRepository.findAll().stream()
                .filter(apartment -> apartment.getStatus() == ApartmentStatus.AVAILABLE)
                .toList();
    }
}
