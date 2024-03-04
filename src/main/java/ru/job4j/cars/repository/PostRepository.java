package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findForLastDay() {
        return crudRepository.query(
                """
                        FROM Post p
                        WHERE (
                                EXTRACT(YEAR FROM p.created),
                                EXTRACT(MONTH FROM p.created),
                                EXTRACT(DAY FROM p.created)
                            ) IN (
                                SELECT
                                    EXTRACT(YEAR FROM MAX(p.created)),
                                    EXTRACT(MONTH FROM MAX(p.created)),
                                    EXTRACT(DAY FROM MAX(p.created))
                                FROM Post p
                            )
                        """,
                Post.class
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

    public List<Post> findByBrand(String brand) {
        return crudRepository.query(
                """
                        SELECT p FROM Post p
                        JOIN p.car c
                        WHERE c.brand = :brand
                        """,
                Post.class,
                Map.of("brand", brand)
        );
    }
}
