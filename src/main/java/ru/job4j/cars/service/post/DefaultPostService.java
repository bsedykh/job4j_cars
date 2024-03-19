package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.post.PostRepository;
import ru.job4j.cars.service.body.BodyService;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.category.CategoryService;
import ru.job4j.cars.service.engine.EngineService;
import ru.job4j.cars.service.file.FileService;
import ru.job4j.cars.service.model.ModelService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DefaultPostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final CategoryService categoryService;
    private final ModelService modelService;
    private final BodyService bodyService;
    private final EngineService engineService;
    private final CarService carService;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findForLastDay() {
        return postRepository.findForLastDay();
    }

    @Override
    public List<Post> findNew() {
        return postRepository.findNew();
    }

    @Override
    public void save(PostDto postDto, FileDto fileDto, User user) {
        var category = categoryService.findById(postDto.categoryId()).orElseThrow();
        var model = modelService.findById(postDto.modelId()).orElseThrow();
        var body = bodyService.findById(postDto.bodyId()).orElseThrow();
        var engine = engineService.findById(postDto.engineId()).orElseThrow();
        var file = fileService.save(fileDto);
        var car = new Car();
        car.setCategory(category);
        car.setModel(model);
        car.setBody(body);
        car.setEngine(engine);
        car.setYear(postDto.year());
        car.setMileage(postDto.mileage());
        carService.save(car);
        var post = new Post();
        post.setDescription(postDto.description());
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post.setUser(user);
        post.setCar(car);
        post.setFiles(Set.of(file));
        post.setPrice(postDto.price());
        postRepository.save(post);
    }

    @Override
    public void closePost(int id, User user) {
        var post = postRepository.findById(id).orElseThrow();
        var author = post.getUser();
        if (!author.equals(user)) {
            throw new IllegalArgumentException(
                    String.format("Post (id = %d) can't be closed by user (%s)."
                                    + " Only author (%s) can close the post.",
                            id, user.getLogin(), author.getLogin())
            );
        }
        post.setClosed(true);
        postRepository.update(post);
    }
}
