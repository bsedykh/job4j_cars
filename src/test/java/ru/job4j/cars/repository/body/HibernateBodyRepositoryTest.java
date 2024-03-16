package ru.job4j.cars.repository.body;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.Utils;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateBodyRepositoryTest {
    private static SessionFactory sessionFactory;
    private static HibernateBodyRepository repository;

    @BeforeAll
    public static void initRepository() {
        sessionFactory = Utils.createSessionFactory();
        repository = new HibernateBodyRepository(
                new CrudRepository(sessionFactory)
        );
    }

    @AfterAll
    public static void closeSessionFactory() {
        Utils.closeSessionFactory(sessionFactory);
    }

    @Test
    public void whenFindAll() {
        var expected = IntStream.rangeClosed(1, 3)
                .mapToObj(id -> repository.findById(id).orElseThrow())
                .toList();
        var actual = repository.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void whenFindById() {
        var expected = new Body(1, "Седан");
        var actual = repository.findById(1).orElseThrow();
        assertThat(actual)
                .usingRecursiveComparison()
                .usingOverriddenEquals()
                .isEqualTo(expected);
    }
}
