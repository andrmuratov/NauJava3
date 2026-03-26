package ru.andrmuratov.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByName() {
        String name = UUID.randomUUID().toString();
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@test.com");
        user.setRole("USER");
        userRepository.save(user);

        List<User> found = userRepository.findByName(name);
        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals(name, found.get(0).getName());
    }

    @Test
    void testCriteriaAPI() {
        String name = UUID.randomUUID().toString();
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@test.com");
        user.setRole("ADMIN");
        userRepository.save(user);

        List<User> found = userRepository.findByNameCriteria(name);
        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals(name, found.get(0).getName());
    }
}