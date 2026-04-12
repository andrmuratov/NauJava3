package ru.andrmuratov.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testFindByUsername() {
        String username = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@test.com");
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode("testPassword"));
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername(username);
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(username);
    }

    @Test
    void testCriteriaAPI() {
        String username = UUID.randomUUID().toString();
        String email = username + "@test.com";
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole("ADMIN");
        user.setPassword(passwordEncoder.encode("testPassword"));
        userRepository.save(user);

        List<User> found = userRepository.findByRoleAndEmailContaining("ADMIN", "@test.com");
        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals(username, found.get(0).getUsername());
    }
}