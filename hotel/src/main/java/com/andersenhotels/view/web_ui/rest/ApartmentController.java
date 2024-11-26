package com.andersenhotels.view.web_ui.rest;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.ApartmentStatus;
import com.andersenhotels.model.storage.jpa.ApartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentRepository apartmentRepository;

    public ApartmentController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable long id) {
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        return apartment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Apartment createApartment(@RequestBody Apartment apartment) {
        apartment.setStatus(ApartmentStatus.AVAILABLE); // Устанавливаем статус по умолчанию
        return apartmentRepository.save(apartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartment> updateApartment(@PathVariable long id, @RequestBody Apartment updatedApartment) {
        return apartmentRepository.findById(id)
                .map(apartment -> {
                    apartment.setPrice(updatedApartment.getPrice());
                    apartment.setStatus(updatedApartment.getStatus());
                    return ResponseEntity.ok(apartmentRepository.save(apartment));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable long id) {
        if (apartmentRepository.existsById(id)) {
            apartmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
