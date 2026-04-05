package ru.andrmuratov.NauJava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Category;

@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {
}