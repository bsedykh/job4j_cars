package ru.job4j.cars.repository.owner;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateOwnerRepository {
    private final CrudRepository crudRepository;

    public List<Owner> findAll() {
        return crudRepository.query("""
                        FROM Owner o
                        """, Owner.class);
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional("""
                FROM Owner o
                WHERE o.id = :id
                """, Owner.class, Map.of("id", id));
    }

    public Owner save(Owner owner) {
        crudRepository.run(session -> session.save(owner));
        return owner;
    }

    public void update(Owner owner) {
        crudRepository.run(session -> session.update(owner));
    }

    public boolean delete(int id) {
        return crudRepository.run("DELETE Owner o WHERE o.id = :id",
                Map.of("id", id)) > 0;
    }
}
