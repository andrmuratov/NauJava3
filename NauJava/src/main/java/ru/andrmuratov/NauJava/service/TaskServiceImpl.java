package ru.andrmuratov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andrmuratov.NauJava.dao.TaskRepository;
import ru.andrmuratov.NauJava.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(String title, String description, Task.Priority priority, Task.Status status) {
        Task task = new Task();
        task.setId(idGenerator.getAndIncrement());
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(status);
        task.setDeadline(LocalDateTime.now().plusDays(7));
        taskRepository.create(task);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.delete(id);
    }

    @Override
    public void updateTask(Long id, String title, String description, Task.Priority priority, Task.Status status) {
        Task task = taskRepository.read(id);
        if (task != null) {
            task.setTitle(title);
            task.setDescription(description);
            task.setPriority(priority);
            task.setStatus(status);
            taskRepository.update(task);
        }
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }
}