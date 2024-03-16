package ru.job4j.cars.repository.car;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.Utils;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarRepositoryTest {
    private static SessionFactory sessionFactory;
    private static HibernateCarRepository carRepository;

    @BeforeAll
    public static void initRepository() {
        sessionFactory = Utils.createSessionFactory();
        carRepository = new HibernateCarRepository(
                new CrudRepository(sessionFactory)
        );
    }

    @AfterAll
    public static void closeSessionFactory() {
        Utils.closeSessionFactory(sessionFactory);
    }

    @AfterEach
    public void clear() {
        for (var car : carRepository.findAll()) {
            carRepository.delete(car.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var category = new Category();
        category.setId(1);
        var model = new Model();
        model.setId(1);
        var body = new Body();
        body.setId(1);
        var engine = new Engine();
        engine.setId(1);

        var car = new Car();
        car.setCategory(category);
        car.setModel(model);
        car.setBody(body);
        car.setEngine(engine);
        car.setYear(2022);
        car.setMileage(10_000);
        carRepository.save(car);

        var dbCar = carRepository.findById(car.getId()).orElseThrow();
        assertThat(dbCar).usingRecursiveComparison()
                .usingOverriddenEquals()
                .isEqualTo(car);
    }
}
