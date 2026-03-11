package ru.andrmuratov.NauJava.service;

import ru.andrmuratov.NauJava.entity.Task;
import java.util.List;

public interface TaskService {
    void createTask(String title, String description, Task.Priority priority, Task.Status status);
    Task findById(Long id);
    void deleteById(Long id);
    void updateTask(Long id, String title, String description, Task.Priority priority, Task.Status status);
    List<Task> findAll();
    List<Task> findByStatus(Task.Status status);
}