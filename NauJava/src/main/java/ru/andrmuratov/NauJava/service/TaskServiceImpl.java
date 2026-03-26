package ru.andrmuratov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andrmuratov.NauJava.repository.TaskRepository;
import ru.andrmuratov.NauJava.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(String title, String description, Task.Priority priority, Task.Status status) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority.name());
        task.setStatus(status.name());
        task.setDeadline(LocalDateTime.now().plusDays(7));
        taskRepository.save(task);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTask(Long id, String title, String description, Task.Priority priority, Task.Status status) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setPriority(priority.name());
            task.setStatus(status.name());
            taskRepository.save(task);
        }
    }

    @Override
    public List<Task> findAll() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return taskRepository.findByStatus(status.name());
    }
}