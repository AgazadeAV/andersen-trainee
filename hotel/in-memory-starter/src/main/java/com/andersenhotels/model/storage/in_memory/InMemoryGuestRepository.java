package com.andersenhotels.model.storage.in_memory;

import com.andersenhotels.model.entities.Guest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("in-memory")
public class InMemoryGuestRepository extends InMemoryRepository<Guest, Long> {
}
