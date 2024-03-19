package ru.job4j.cars.repository.post;

import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll();

    Optional<Post> findById(int id);

    List<Post> findForLastDay();

    List<Post> findWithPhoto();

    List<Post> findByBrand(Brand brand);

    List<Post> findNew();

    Post save(Post post);

    void update(Post post);

    void delete(Post post);
}
