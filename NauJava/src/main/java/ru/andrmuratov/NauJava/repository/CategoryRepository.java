package ru.andrmuratov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}