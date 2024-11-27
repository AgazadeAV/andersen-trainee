package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.ApartmentStatus;
import com.andersenhotels.model.storage.jpa.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

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

    public Apartment updateApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(long id) {
        if (apartmentRepository.existsById(id)) {
            apartmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Apartment not found with ID: " + id);
        }
    }

    public List<Apartment> getAvailableApartments() {
        return apartmentRepository.findAll().stream()
                .filter(apartment -> apartment.getStatus() == ApartmentStatus.AVAILABLE)
                .toList();
    }
}
