package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Set;

public class CarUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {

            var crudRepository = new CrudRepository(sf);
            var engineRepository = new EngineRepository(crudRepository);
            var userRepository = new UserRepository(sf);
            var ownerRepository = new OwnerRepository(crudRepository);
            var carRepository = new CarRepository(crudRepository);

            var engine = new Engine();
            engine.setName("V6");
            engineRepository.save(engine);

            var user = new User();
            user.setLogin("ivan");
            user.setPassword("123");
            userRepository.create(user);

            var owner = new Owner();
            owner.setName("Ivan");
            owner.setUser(user);
            ownerRepository.save(owner);

            var car = new Car();
            car.setName("Mercedes E200");
            car.setEngine(engine);
            car.setOwners(Set.of(owner));
            carRepository.save(car);

            var cars = carRepository.findAll();
            cars.forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
