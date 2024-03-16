package ru.job4j.cars.repository.post;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.Utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePostRepositoryTest {
    private static SessionFactory sessionFactory;
    private static CrudRepository crudRepository;
    private static HibernatePostRepository postRepository;

    /**
     * The list contains exactly two cars:
     * <ul>
     *     <li>1st car has brand with id = 1</li>
     *     <li>2nd car has brand with id = 2</li>
     * </ul>
     */
    private List<Car> cars;

    private final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @BeforeAll
    public static void initRepository() {
        sessionFactory = Utils.createSessionFactory();
        crudRepository = new CrudRepository(sessionFactory, "post");
        postRepository = new HibernatePostRepository(crudRepository);
    }

    @AfterAll
    public static void closeSessionFactory() {
        Utils.closeSessionFactory(sessionFactory);
    }

    @BeforeEach
    public void init() {
        var category = new Category();
        category.setId(1);
        var model1 = new Model();
        model1.setId(1);
        var model2 = new Model();
        model2.setId(3);
        var body = new Body();
        body.setId(1);
        var engine1 = new Engine();
        engine1.setId(1);
        var engine2 = new Engine();
        engine2.setId(2);

        cars = crudRepository.tx(session -> {
            var car1 = new Car();
            car1.setCategory(category);
            car1.setModel(model1);
            car1.setBody(body);
            car1.setEngine(engine1);
            car1.setYear(2022);
            car1.setMileage(10_000);
            session.persist(car1);

            var car2 = new Car();
            car2.setCategory(category);
            car2.setModel(model2);
            car2.setBody(body);
            car2.setEngine(engine2);
            car2.setYear(2022);
            car2.setMileage(10_000);
            session.persist(car2);

            return List.of(car1, car2);
        });
    }

    @AfterEach
    public void clear() {
        for (var post : postRepository.findAll()) {
            postRepository.delete(post);
        }
        crudRepository.run(session -> cars.forEach(session::remove));
    }

    @Test
    public void whenSaveThenGetSame() {
        var post = savePost("Post");
        var dbPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(dbPost).usingRecursiveComparison()
                .ignoringFields("participates")
                .usingOverriddenEquals()
                .isEqualTo(post);
    }

    @Test
    public void whenSaveSeveralThenFindAll() {
        var post1 = savePost("Post1");
        var post2 = savePost("Post2");
        var post3 = savePost("Post3");
        var result = postRepository.findAll();
        assertThat(result).containsExactlyInAnyOrder(post1, post2, post3);
    }

    @Test
    public void whenSaveSeveralThenFindForLastDay() {
        var post1 = savePost("Post1");
        var post2 = savePost("Post2");
        savePost("Post3", now.minusDays(2), cars.get(0), false);
        var result = postRepository.findForLastDay();
        assertThat(result).containsExactlyInAnyOrder(post1, post2);
    }

    @Test
    public void whenSaveSeveralThenFindWithPhoto() {
        var post1 = savePost("Post1");
        var post2 = savePost("Post2");
        savePost("Post3", now, cars.get(0), false);
        var result = postRepository.findWithPhoto();
        assertThat(result).containsExactlyInAnyOrder(post1, post2);
    }

    @Test
    public void whenSaveSeveralThenFindByBrand() {
        var post1 = savePost("Post1");
        var post2 = savePost("Post2");
        savePost("Post3", now, cars.get(1), false);
        var brand = new Brand();
        brand.setId(1);
        var result = postRepository.findByBrand(brand);
        assertThat(result).containsExactlyInAnyOrder(post1, post2);
    }

    @Test
    public void whenNoPostsThenNothingFound() {
        assertThat(postRepository.findAll()).isEmpty();
        assertThat(postRepository.findById(1)).isEmpty();
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var post = savePost("Post");
        postRepository.delete(post);
        assertThat(postRepository.findById(post.getId())).isEmpty();
    }

    @Test
    public void whenUpdateClosedThenGetClosedPost() {
        var post = savePost("Post");
        post.setClosed(true);
        postRepository.update(post);
        var dbPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(dbPost.isClosed()).isTrue();
    }

    private Post savePost(String description) {
        return savePost(description, now, cars.get(0), true);
    }

    private Post savePost(String description,
                          LocalDateTime created,
                          Car car,
                          boolean createFiles) {
        var user = new User();
        user.setId(1);
        var post = new Post();
        post.setDescription(description);
        post.setCreated(created);
        post.setUser(user);
        post.setCar(car);
        post.setPrice(100);
        if (createFiles) {
            var file1 = new File();
            file1.setName("file1.txt");
            file1.setPath("files/file1.txt");
            var file2 = new File();
            file2.setName("file2.txt");
            file2.setPath("files/file2.txt");
            post.setFiles(List.of(file1, file2));
        }
        postRepository.save(post);
        return post;
    }
}
