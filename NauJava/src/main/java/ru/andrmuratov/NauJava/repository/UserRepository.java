package ru.andrmuratov.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.CrudRepository;
import ru.andrmuratov.NauJava.dao.UserRepositoryCustom;
import ru.andrmuratov.NauJava.entity.User;
import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
    List<User> findByName(String name);
    List<User> findByRoleAndEmailContaining(String role, String emailDomain);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRoleJPQL(String role);
}