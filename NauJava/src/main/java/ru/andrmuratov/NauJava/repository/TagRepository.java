package ru.andrmuratov.NauJava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Tag;

@RepositoryRestResource(path = "tags")
public interface TagRepository extends CrudRepository<Tag, Long> {
}