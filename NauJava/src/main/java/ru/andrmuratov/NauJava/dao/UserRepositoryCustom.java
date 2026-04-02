package ru.andrmuratov.NauJava.dao;

import ru.andrmuratov.NauJava.entity.User;
import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByNameCriteria(String name);
    List<User> findByRoleCriteria(String role);
}