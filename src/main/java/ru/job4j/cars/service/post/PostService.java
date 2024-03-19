package ru.job4j.cars.service.post;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Optional<Post> findById(int id);

    List<Post> findForLastDay();

    List<Post> findNew();

    void save(PostDto postDto, FileDto fileDto, User user);

    void closePost(int id, User user);
}
