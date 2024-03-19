package ru.job4j.cars.service.model;

import ru.job4j.cars.model.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    List<Model> findAll();

    Optional<Model> findById(int id);
}
