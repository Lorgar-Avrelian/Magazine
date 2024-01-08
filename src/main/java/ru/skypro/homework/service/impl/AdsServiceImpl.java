package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AdsServiceImpl implements AdsService {
    @Value("${ad.image.dir.path}")
    private String imageDir;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;
    private static final Logger log = Logger.getLogger(AdsServiceImpl.class);

    public AdsServiceImpl(AdRepository adRepository, CommentRepository commentRepository, UserRepository userRepository, AdMapper adMapper, CommentMapper commentMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public AdsDTO getAll() {
        List<Ad> adsList;
        try {
            adsList = adRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return adMapper.adsListToAdsDto(adsList);
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO adDTO, MultipartFile image) {
        User user = getUser();
        if (user == null) {
            return null;
        }
        Ad ad;
        Ad newAd = adMapper.createOrUpdateAdDtoToAd(adDTO);
        try {
            ad = adRepository.findByAuthorAndTitleAndPriceAndDescription(user.getId(), newAd.getTitle(), newAd.getPrice(), newAd.getDescription());
        } catch (NoSuchElementException e) {
            ad = newAd;
        }
        Path filePath = null;
        try {
            filePath = Path.of(imageDir, "user_" + user.getId() + "_ad_" + ad.getPk() + "." + getExtension(image.getOriginalFilename()));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        ad.setImage(String.valueOf(filePath));
        ad = adRepository.save(ad);
        if (ad != null) {
            return adMapper.adToAdDto(ad);
        } else {
            return null;
        }
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public ExtendedAdDTO getAd(Integer pk) {
        Ad ad = adRepository.findById(pk).get();
        return adMapper.adToExtendedAd(ad, ad.getAuthor());
    }

    @Override
    public boolean deleteAd(Integer pk) {
        User user = getUser();
        if (user == null) {
            return false;
        }
        Ad ad = getThatAd(pk);
        if (ad != null) {
            try {
                adRepository.delete(ad);
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public AdDTO updateAd(Integer pk, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        Ad adUpdate = adMapper.createOrUpdateAdDtoToAd(createOrUpdateAdDTO);
        Ad ad = getThatAd(pk);
        if (ad != null) {
            ad.setTitle(adUpdate.getTitle());
            ad.setPrice(adUpdate.getPrice());
            ad.setDescription(adUpdate.getDescription());
            adRepository.save(ad);
        } else {
            return null;
        }
        return adMapper.adToAdDto(ad);
    }

    @Override
    public AdsDTO getAllMine() {
        User user = getUser();
        if (user == null) {
            return null;
        }
        List<Ad> adsList;
        try {
            adsList = adRepository.findByPk(user.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return adMapper.adsListToAdsDto(adsList);
    }

    @Override
    public String updateAdImage(Integer pk, MultipartFile image) {
        Ad ad = getThatAd(pk);
        AdDTO adDTO = null;
        if (ad != null) {
            CreateOrUpdateAdDTO createOrUpdateAdDTO = adMapper.adToCreateOrUpdateDto(ad);
            adDTO = addAd(createOrUpdateAdDTO, image);
        }
        if (adDTO != null) {
            return adDTO.getImage();
        } else {
            return null;
        }
    }

    @Override
    public CommentsDTO getAdComments(Integer pk) {
        Ad ad = getThatAd(pk);
        if (ad == null) {
            return null;
        }
        List<Comment> commentsList = commentRepository.findByAdPk(pk);
        if (commentsList.isEmpty()) {
            return null;
        } else {
            return commentMapper.commentListToCommentsDto(commentsList);
        }
    }

    @Override
    public CommentDTO addComment(Integer pk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = getUser();
        if (user == null) {
            return null;
        }
        Ad ad = getThatAd(pk);
        if (ad == null) {
            return null;
        }
        Comment comment = new Comment(null, user, user.getImage(), user.getFirstName(), new Date().getTime(), createOrUpdateCommentDTO.getText(), pk);
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public boolean deleteComment(Integer adPk, Integer commentPk) {
        Ad ad = getThatAd(adPk);
        if (ad == null) {
            return false;
        }
        Comment comment = getComment(commentPk);
        if (comment != null) {
            commentRepository.delete(comment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CommentDTO updateComment(Integer adPk, Integer commentPk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Ad ad = getThatAd(adPk);
        if (ad == null) {
            return null;
        }
        Comment comment = getComment(commentPk);
        if (comment != null) {
            comment.setText(createOrUpdateCommentDTO.getText());
        } else {
            return null;
        }
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    private User getUser() {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return user;
    }

    private Ad getThatAd(Integer pk) {
        Ad ad;
        try {
            ad = adRepository.findById(pk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
        return ad;
    }

    private Comment getComment(Integer pk) {
        Comment comment;
        try {
            comment = commentRepository.findById(pk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
        return comment;
    }
}