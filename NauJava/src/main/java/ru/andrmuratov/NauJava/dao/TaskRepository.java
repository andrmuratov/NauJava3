package ru.andrmuratov.NauJava.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrmuratov.NauJava.entity.Task;

import java.util.List;

@Component
public class TaskRepository implements CrudRepository<Task, Long> {

    private final List<Task> taskContainer;

    @Autowired
    public TaskRepository(List<Task> taskContainer) {
        this.taskContainer = taskContainer;
    }

    @Override
    public void create(Task task) {
        taskContainer.add(task);
    }

    @Override
    public Task read(Long id) {
        for (Task task : taskContainer) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void update(Task task) {
        for (int i = 0; i < taskContainer.size(); i++) {
            if (taskContainer.get(i).getId().equals(task.getId())) {
                taskContainer.set(i, task);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        taskContainer.removeIf(task -> task.getId().equals(id));
    }

    // Дополнительный метод для получения всех задач
    public List<Task> findAll() {
        return taskContainer;
    }
}