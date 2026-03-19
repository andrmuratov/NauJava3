package ru.andrmuratov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}