package ru.andrmuratov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("message", "User already exists");
            return "registration";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}