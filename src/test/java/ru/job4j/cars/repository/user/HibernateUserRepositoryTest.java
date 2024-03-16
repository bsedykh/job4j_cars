package ru.job4j.cars.repository.user;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.Utils;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateUserRepositoryTest {
    private static SessionFactory sessionFactory;
    private static HibernateUserRepository userRepository;

    @BeforeAll
    public static void initRepository() {
        sessionFactory = Utils.createSessionFactory();
        userRepository = new HibernateUserRepository(sessionFactory);
    }

    @AfterAll
    public static void closeSessionFactory() {
        Utils.closeSessionFactory(sessionFactory);
    }

    @Test
    public void whenFindByLoginAndPasswordThenGetUser() {
        var expected = new User(1, "Ivanov", "root");
        var actual = userRepository.findByLoginAndPassword("Ivanov",
                "root").orElseThrow();
        assertThat(actual)
                .usingRecursiveComparison()
                .usingOverriddenEquals()
                .isEqualTo(expected);
    }
}
