package ru.job4j.cars.repository.category;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.Utils;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCategoryRepositoryTest {
    private static SessionFactory sessionFactory;
    private static HibernateCategoryRepository repository;

    @BeforeAll
    public static void initRepository() {
        sessionFactory = Utils.createSessionFactory();
        repository = new HibernateCategoryRepository(
                new CrudRepository(sessionFactory)
        );
    }

    @AfterAll
    public static void closeSessionFactory() {
        Utils.closeSessionFactory(sessionFactory);
    }

    @Test
    public void whenFindAll() {
        var expected = IntStream.rangeClosed(1, 2)
                .mapToObj(id -> repository.findById(id).orElseThrow())
                .toList();
        var actual = repository.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void whenFindById() {
        var expected = new Category(1, "Новые");
        var actual = repository.findById(1).orElseThrow();
        assertThat(actual)
                .usingRecursiveComparison()
                .usingOverriddenEquals()
                .isEqualTo(expected);
    }
}
