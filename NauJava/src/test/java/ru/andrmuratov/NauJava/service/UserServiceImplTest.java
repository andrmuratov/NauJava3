package ru.andrmuratov.NauJava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.andrmuratov.NauJava.entity.User;
import ru.andrmuratov.NauJava.repository.TaskRepository;
import ru.andrmuratov.NauJava.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("test@example.com");
        testUser.setRole("USER");
    }

    @Test
    void testCreateUserWithTask_Success() {
        when(passwordEncoder.encode("defaultPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(taskRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> userService.createUserWithTask("testuser", "test@example.com", "USER", "Test Task"));

        verify(userRepository, times(1)).save(any(User.class));
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void testDeleteUserWithTasks_Success() {
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUserWithTasks(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateUserWithTask_RepositoryThrowsException() {
        when(passwordEncoder.encode("defaultPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.createUserWithTask("testuser", "test@example.com", "USER", "Test Task"));
    }
}