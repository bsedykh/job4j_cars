package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class PostUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            create(user, sf);

            var now = LocalDateTime.now();
            var post = new Post();
            post.setDescription("Post1");
            post.setCreated(now);
            post.setUser(user);
            post.setPrices(List.of(
                    new PriceHistory(0, 100, 200, now),
                    new PriceHistory(0, 200, 300, now.plusDays(1)),
                    new PriceHistory(0, 300, 400, now.plusDays(2))
            ));
            create(post, sf);

            var stored = sf.openSession()
                    .createQuery("from Post where id = :fId", Post.class)
                    .setParameter("fId", post.getId())
                    .getSingleResult();
            stored.getPrices().forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> void create(T model, SessionFactory sf) {
        var session = sf.openSession();
        session.beginTransaction();
        session.persist(model);
        session.getTransaction().commit();
        session.close();
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        var session = sf.openSession();
        session.beginTransaction();
        var list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
