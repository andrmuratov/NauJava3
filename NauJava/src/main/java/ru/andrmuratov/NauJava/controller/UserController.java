package ru.andrmuratov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/by-name")
    public List<User> findByName(@RequestParam String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/by-role")
    public List<User> findByRole(@RequestParam String role) {
        return userRepository.findByRoleJPQL(role);
    }
}