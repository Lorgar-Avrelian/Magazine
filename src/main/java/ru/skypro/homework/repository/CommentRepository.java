package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * {@link JpaRepository} for storage DAO {@link Comment} and operating with it. <br>
 * <br>
 * <hr>
 * <br>
 * {@link JpaRepository} для хранения DAO {@link Comment} и работы с ней. <br>
 * <br>
 *
 * @see JpaRepository
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByAdAuthor(User user);

    List<Comment> findByAd(Ad ad);

    Optional<Comment> findByPk(Integer pk);
}
