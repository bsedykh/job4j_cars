package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

    @AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public List<Engine> findAll() {
        return crudRepository.query("""
                        FROM Engine e
                        """, Engine.class);
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional("""
                FROM Engine e
                WHERE e.id = :id
                """, Engine.class, Map.of("id", id));
    }

    public Engine save(Engine engine) {
        crudRepository.run(session -> session.save(engine));
        return engine;
    }

    public void update(Engine engine) {
        crudRepository.run(session -> session.update(engine));
    }

    public boolean delete(int id) {
        return crudRepository.run("DELETE Engine e WHERE e.id = :id",
                Map.of("id", id)) > 0;
    }
}
