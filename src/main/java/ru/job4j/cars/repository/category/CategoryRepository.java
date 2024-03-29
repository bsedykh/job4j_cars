package ru.job4j.cars.repository.category;

import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(int id);
}
