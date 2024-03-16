package ru.job4j.cars.repository.body;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateBodyRepository implements BodyRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Body> findAll() {
        return crudRepository.query(
                """
                        SELECT b FROM Body b
                        """,
                Body.class
        );
    }

    @Override
    public Optional<Body> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT b FROM Body b
                        WHERE b.id = :id
                        """,
                Body.class,
                Map.of("id", id)
        );
    }
}
