package ru.andrmuratov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/by-username")
    public Optional<User> findByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/by-role")
    public List<User> findByRole(@RequestParam String role) {
        return userRepository.findByRoleJPQL(role);
    }

    @GetMapping("/by-role-email")
    public List<User> findByRoleAndEmail(@RequestParam String role, @RequestParam String email) {
        return userRepository.findByRoleAndEmailContaining(role, email);
    }
}