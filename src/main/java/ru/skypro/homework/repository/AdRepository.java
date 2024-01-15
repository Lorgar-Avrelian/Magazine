package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * {@link JpaRepository} for storage DAO {@link Ad} and operating with it. <br>
 * <br>
 * <hr>
 * <br>
 * {@link JpaRepository} для хранения DAO {@link Ad} и работы с ней. <br>
 * <br>
 *
 * @see JpaRepository
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    List<Ad> findByAuthor(User user);

    Optional<Ad> findByPk(Integer pk);
}
