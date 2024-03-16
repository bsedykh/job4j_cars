package ru.job4j.cars.repository.engine;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineRepository {
    List<Engine> findAll();

    Optional<Engine> findById(int id);

    Engine save(Engine engine);

    void update(Engine engine);

    boolean delete(int id);
}
