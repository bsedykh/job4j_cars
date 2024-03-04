package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    public List<Car> findAll() {
        return crudRepository.query("""
                        FROM Car c
                            JOIN FETCH c.owners
                        """, Car.class)
                .stream().distinct().toList();
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional("""
                FROM Car c
                    JOIN FETCH c.owners
                WHERE c.id = :id
                """, Car.class, Map.of("id", id));
    }

    public Car save(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    public boolean delete(int id) {
        return crudRepository.run("DELETE Car c WHERE c.id = :id",
                Map.of("id", id)) > 0;
    }
}
