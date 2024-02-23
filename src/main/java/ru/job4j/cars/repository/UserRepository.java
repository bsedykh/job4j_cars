package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        executeInTransaction(session -> session.save(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        executeInTransaction(session -> session.createQuery("""
                        UPDATE User SET login = :login, password = :password
                        WHERE id = :id
                        """)
                .setParameter("login", user.getLogin())
                .setParameter("password", user.getPassword())
                .setParameter("id", user.getId())
                .executeUpdate());
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        executeInTransaction(session -> session.createQuery(
                        "DELETE User WHERE id = :id")
                .setParameter("id", userId)
                .executeUpdate());
    }

    private void executeInTransaction(Consumer<Session> action) {
        try (var session = sf.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                action.accept(session);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    throw e;
                }
            }
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        try (var session = sf.openSession()) {
            var query = session.createQuery(
                    "from User ORDER BY id", User.class);
            return query.list();
        }
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        try (var session = sf.openSession()) {
            var query = session.createQuery(
                    "from User u WHERE u.id = :id", User.class);
            query.setParameter("id", userId);
            return query.uniqueResultOptional();
        }
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        try (var session = sf.openSession()) {
            var query = session.createQuery(
                    "from User u WHERE u.login LIKE :login", User.class);
            query.setParameter("login", "%%%s%%".formatted(key));
            return query.list();
        }
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        try (var session = sf.openSession()) {
            var query = session.createQuery(
                    "from User u WHERE u.login = :login", User.class);
            query.setParameter("login", login);
            return query.uniqueResultOptional();
        }
    }
}
