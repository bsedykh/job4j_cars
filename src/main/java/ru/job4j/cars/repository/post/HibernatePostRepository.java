package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findForLastDay() {
        return crudRepository.query(
                """
                        FROM Post p
                        WHERE p.created > :startDate
                        """,
                Post.class,
                Map.of("startDate", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                """
                        SELECT p FROM Post p
                        JOIN p.files f
                        """,
                Post.class
        );
    }

    public List<Post> findByBrand(Brand brand) {
        return crudRepository.query(
                """
                        SELECT p FROM Post p
                        JOIN p.car c
                            JOIN c.model m
                        WHERE m.brand = :brand
                        """,
                Post.class,
                Map.of("brand", brand)
        );
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query(
                """
                        SELECT p FROM Post p
                        """,
                Post.class
        );
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT p FROM Post p
                        WHERE p.id = :id
                        """,
                Post.class,
                Map.of("id", id)
        );
    }

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(Post post) {
        crudRepository.run(session -> session.remove(post));
    }
}
