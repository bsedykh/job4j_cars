package ru.job4j.cars.repository.body;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyRepository {
    List<Body> findAll();

    Optional<Body> findById(int id);
}
