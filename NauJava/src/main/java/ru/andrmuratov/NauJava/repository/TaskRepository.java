package ru.andrmuratov.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.entity.Task;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByStatus(String status);
    List<Task> findByUserId(Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.email = :email")
    List<Task> findByUserEmailJPQL(String email);
}