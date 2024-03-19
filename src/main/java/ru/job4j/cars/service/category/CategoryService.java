package ru.job4j.cars.service.category;

import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(int id);
}
