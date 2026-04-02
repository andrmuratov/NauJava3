package ru.andrmuratov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           TaskRepository taskRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createUserWithTask(String username, String email, String role, String taskTitle) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("defaultPassword"));
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