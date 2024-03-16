package ru.job4j.cars.repository.model;

import ru.job4j.cars.model.Model;

import java.util.List;
import java.util.Optional;

public interface ModelRepository {
    List<Model> findAll();

    Optional<Model> findById(int id);
}
