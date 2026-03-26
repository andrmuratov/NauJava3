package ru.andrmuratov.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;
import ru.andrmuratov.NauJava.service.UserService;

import java.util.UUID;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUserWithTask() {
        String name = UUID.randomUUID().toString();
        String email = name + "@test.com";
        userService.createUserWithTask(name, email, "USER", "Test Task");

        User found = userRepository.findByRoleJPQL("USER").stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(name, found.getName());
    }
}