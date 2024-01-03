package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;

public interface AdsService {
    Ads getAll();

    ru.skypro.homework.dto.Ad addAd(CreateOrUpdateAd ad, MultipartFile image);
}
