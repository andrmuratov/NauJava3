package ru.andrmuratov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrmuratov.NauJava.entity.Task;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.TaskRepository;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public void createUserWithTask(String name, String email, String role, String taskTitle) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);

        Task task = new Task();
        task.setTitle(taskTitle);
        task.setStatus("TODO");
        task.setDeadline(LocalDateTime.now().plusDays(7));
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteUserWithTasks(Long userId) {
        userRepository.deleteById(userId);
    }
}