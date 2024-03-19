package ru.job4j.cars.service.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.car.CarRepository;

@Service
@AllArgsConstructor
public class DefaultCarService implements CarService {
    private final CarRepository carRepository;
    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }
}
