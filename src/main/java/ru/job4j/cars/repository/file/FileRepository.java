package ru.job4j.cars.repository.file;

import ru.job4j.cars.model.File;

import java.util.Optional;

public interface FileRepository {
    File save(File file);

    Optional<File> findById(int id);
}
