package ru.andrmuratov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
}