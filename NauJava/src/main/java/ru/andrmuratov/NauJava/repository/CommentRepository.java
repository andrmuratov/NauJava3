package ru.andrmuratov.NauJava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Comment;

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends CrudRepository<Comment, Long> {
}