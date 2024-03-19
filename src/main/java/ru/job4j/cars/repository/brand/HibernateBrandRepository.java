package ru.job4j.cars.repository.brand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateBrandRepository implements BrandRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Brand> findAll() {
        return crudRepository.query(
                """
                        SELECT b FROM Brand b
                        """,
                Brand.class
        );
    }

    @Override
    public Optional<Brand> findById(int id) {
        return crudRepository.optional(
                """
                        SELECT b FROM Brand b
                        WHERE b.id = :id
                        """,
                Brand.class,
                Map.of("id", id)
        );
    }
}
