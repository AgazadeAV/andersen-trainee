package com.andersenhotels.model.storage.in_memory;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.HashMap;

@Repository
public class InMemoryRepository<T, ID> {
    private final Map<ID, T> storage = new HashMap<>();

    public T save(ID id, T entity) {
        storage.put(id, entity);
        return entity;
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(ID id) {
        storage.remove(id);
    }
}
