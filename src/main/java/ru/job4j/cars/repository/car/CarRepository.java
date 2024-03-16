package ru.job4j.cars.repository.car;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();

    Optional<Car> findById(int id);

    Car save(Car car);

    void update(Car car);

    boolean delete(int id);
}
