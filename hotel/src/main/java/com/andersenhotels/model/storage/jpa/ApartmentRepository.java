package com.andersenhotels.model.storage.jpa;

import com.andersenhotels.model.entities.Apartment;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
