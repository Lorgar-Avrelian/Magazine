package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.User;

import java.util.Optional;

/**
 * {@link JpaRepository} for storage DAO {@link User} and operating with it. <br>
 * <br>
 * <hr>
 * <br>
 * {@link JpaRepository} для хранения DAO {@link User} и работы с ней. <br>
 * <br>
 *
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
