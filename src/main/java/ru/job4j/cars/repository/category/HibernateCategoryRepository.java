package ru.job4j.cars.repository.category;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query(
                """
                        SELECT c FROM Category c
                        """,
                Category.class
        );
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT c FROM Category c
                        WHERE c.id = :id
                        """,
                Category.class,
                Map.of("id", id)
        );
    }
}
