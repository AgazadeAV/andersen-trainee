package com.andersenhotels.model.storage.in_memory;

import com.andersenhotels.model.entities.Reservation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("in-memory")
public class InMemoryReservationRepository extends InMemoryRepository<Reservation, Long> {
}
