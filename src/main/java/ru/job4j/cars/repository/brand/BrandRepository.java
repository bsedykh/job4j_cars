package ru.job4j.cars.repository.brand;

import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    List<Brand> findAll();

    Optional<Brand> findById(int id);
}
