package com.andersenhotels.model.storage.jpa;
import com.andersenhotels.model.entities.Hotel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
