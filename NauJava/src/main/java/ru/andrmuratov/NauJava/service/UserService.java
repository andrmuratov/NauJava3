package ru.andrmuratov.NauJava.service;

public interface UserService {
    void createUserWithTask(String name, String email, String role, String taskTitle);
    void deleteUserWithTasks(Long userId);
}