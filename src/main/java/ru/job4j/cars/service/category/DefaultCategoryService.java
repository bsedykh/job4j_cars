package ru.job4j.cars.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.repository.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }
}
