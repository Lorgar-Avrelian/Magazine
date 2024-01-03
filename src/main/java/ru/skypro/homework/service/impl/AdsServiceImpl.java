package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;
@Service
public class AdsServiceImpl implements AdsService {
    @Value("${ad.image.dir.path}")
    private String imageDir;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final AdMapper adMapper;
    private static final Logger log = Logger.getLogger(AdsServiceImpl.class);

    public AdsServiceImpl(AdRepository adRepository, CommentRepository commentRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.adMapper = adMapper;
    }

    @Override
    public Ads getAll() {
        List<Ad> adsList = null;
        try {
            adsList = adRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return adMapper.adsListToAdsDto(adsList);
    }

    @Override
    public ru.skypro.homework.dto.Ad addAd(CreateOrUpdateAd ad, MultipartFile image) {
//        User user = userService.getUser(id);
//
//        Path filePath = Path.of(imageDir, id + "." + getExtension(file.getOriginalFilename()));
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//
//        try (
//                InputStream is = file.getInputStream();
//                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
//        ) {
//            bis.transferTo(bos);
//        }
//
//        Avatar avatar = getAvatar(id);
//        avatar.setStudent(student);
//        avatar.setFilePath(filePath.toString());
//        avatar.setFileSize(file.getSize());
//        avatar.setMediaType(file.getContentType());
//        avatar.setData(generateImagePreview(filePath));
//
//        avatarRepository.save(avatar);
        return null;
    }
}
