package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;

public interface AdsService {
    AdsDTO getAll();

    AdDTO addAd(CreateOrUpdateAdDTO ad, MultipartFile image);
}
