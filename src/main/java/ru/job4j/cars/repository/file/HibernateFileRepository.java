package ru.job4j.cars.repository.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateFileRepository implements FileRepository {
    private final CrudRepository crudRepository;

    @Override
    public File save(File file) {
        crudRepository.run(session -> session.persist(file));
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT f FROM File f
                        WHERE f.id = :id
                        """,
                File.class,
                Map.of("id", id)
        );
    }
}
