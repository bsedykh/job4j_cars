package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class CrudRepository {
    private final SessionFactory sf;

    private final String entityGraph;

    public CrudRepository(SessionFactory sf) {
        this(sf, null);
    }

    public CrudRepository(SessionFactory sf, String entityGraph) {
        this.sf = sf;
        this.entityGraph = entityGraph;
    }

    public void run(Consumer<Session> command) {
        tx(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    public int run(String query, Map<String, Object> args) {
        Function<Session, Integer> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate();
        };
        return tx(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            setEntityGraph(session, sq);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            setEntityGraph(session, sq);
            return sq.list();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            setEntityGraph(session, sq);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        try (var session = sf.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                T rsl = command.apply(session);
                transaction.commit();
                return rsl;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    private void setEntityGraph(Session session, Query<?> sq) {
        if (entityGraph != null) {
            sq.setHint("javax.persistence.fetchgraph",
                    session.getEntityGraph(entityGraph));
        }
    }
}
