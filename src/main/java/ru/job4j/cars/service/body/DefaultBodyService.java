package ru.job4j.cars.service.body;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.body.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultBodyService implements BodyService {
    private final BodyRepository bodyRepository;

    @Override
    public List<Body> findAll() {
        return bodyRepository.findAll();
    }

    @Override
    public Optional<Body> findById(int id) {
        return bodyRepository.findById(id);
    }
}
