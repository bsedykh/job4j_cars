package ru.job4j.cars.repository.model;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateModelRepository implements ModelRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Model> findAll() {
        return crudRepository.query(
                """
                        SELECT m FROM Model m
                        """,
                Model.class
        );
    }

    @Override
    public Optional<Model> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT m FROM Model m
                        WHERE m.id = :id
                        """,
                Model.class,
                Map.of("id", id)
        );
    }
}
