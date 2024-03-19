package ru.job4j.cars.service.engine;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.engine.EngineRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }
}
