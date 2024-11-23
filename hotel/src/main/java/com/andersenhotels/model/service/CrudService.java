package com.andersenhotels.model.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T create(T entity);

    Optional<T> read(ID id);

    void update(T entity);

    void delete(ID id);

    List<T> findAll();

    boolean existsById(ID id);
}
