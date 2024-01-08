package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    Ad findByAuthorIdAndTitleAndPriceAndDescription(Integer authorId, String title, int price, String description);

    List<Ad> findByPk(Integer authorId);
}
