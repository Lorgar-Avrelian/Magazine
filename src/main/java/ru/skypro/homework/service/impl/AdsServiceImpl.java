package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.HttpServletResponse;
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
        if (!adsList.isEmpty()) {
            return adMapper.adsListToAdsDto(adsList);
        } else {
            return null;
        }
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO adDTO, MultipartFile image) {
        User user = getUser();
        if (user == null) {
            return null;
        }
        Ad ad = adMapper.createOrUpdateAdDtoToAd(adDTO);
        ad.setAuthor(user);
        ad = adRepository.save(ad);
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
        return adMapper.adToAdDto(ad);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public ExtendedAdDTO getAd(Integer pk) {
        Ad ad = null;
        try {
            ad = adRepository.findByPk(pk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (ad != null) {
            return adMapper.adToExtendedAd(ad, ad.getAuthor());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteAd(Integer pk) {
        User user = getUser();
        Ad ad = getThatAd(pk);
        if (user != null && ad != null) {
            user = userCheck(user, ad);
        } else {
            return false;
        }
        if (user == null) {
            return false;
        }
        try {
            adRepository.delete(ad);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public AdDTO updateAd(Integer pk, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        User user = getUser();
        Ad adUpdate = adMapper.createOrUpdateAdDtoToAd(createOrUpdateAdDTO);
        Ad ad = getThatAd(pk);
        if (ad != null && ad.getAuthor().equals(user)) {
            ad.setTitle(adUpdate.getTitle());
            ad.setPrice(adUpdate.getPrice());
            ad.setDescription(adUpdate.getDescription());
            ad = adRepository.save(ad);
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
            adsList = adRepository.findByAuthor(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return adMapper.adsListToAdsDto(adsList);
    }

    @Override
    public String updateAdImage(Integer pk, MultipartFile image) {
        User user = getUser();
        Ad ad = getThatAd(pk);
        if (ad != null && ad.getAuthor().equals(user)) {
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
            adRepository.save(ad);
            return String.valueOf(filePath);
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
        List<Comment> commentsList = commentRepository.findByAd(ad);
        if (commentsList.isEmpty()) {
            return null;
        } else {
            return commentMapper.commentListToCommentsDto(commentsList);
        }
    }

    @Override
    public CommentDTO addComment(Integer pk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = getUser();
        Ad ad = getThatAd(pk);
        if (user == null || ad == null) {
            return null;
        }
        Comment comment = new Comment(null, user, user.getImage(), user.getFirstName(), new Date().getTime(), createOrUpdateCommentDTO.getText(), ad);
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public boolean deleteComment(Integer adPk, Integer commentPk) {
        User user = getUser();
        Ad ad = getThatAd(adPk);
        if (user == null || ad == null) {
            return false;
        }
        Comment comment = getComment(commentPk);
        if (comment != null) {
            user = userCheck(user, comment);
        } else {
            return false;
        }
        if (user == null) {
            return false;
        }
        commentRepository.delete(comment);
        return true;
    }

    @Override
    public CommentDTO updateComment(Integer adPk, Integer commentPk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = getUser();
        Ad ad = getThatAd(adPk);
        if (user == null || ad == null) {
            return null;
        }
        Comment comment = null;
        try {
            comment = commentRepository.findByPk(commentPk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (comment == null) {
            comment = new Comment(commentPk, user, user.getImage(), user.getFirstName(), new Date().getTime(), createOrUpdateCommentDTO.getText(), ad);
        } else {
            user = userCheck(user, comment);
            if (user == null) {
                return null;
            }
            comment.setText(createOrUpdateCommentDTO.getText());
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
            ad = adRepository.findByPk(pk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
        return ad;
    }

    private Comment getComment(Integer pk) {
        Comment comment;
        try {
            comment = commentRepository.findByPk(pk).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
        return comment;
    }

    private User userCheck(User user, Ad ad) {
        if (user.getRole().equals(Role.USER) && ad.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            return user;
        } else {
            return null;
        }
    }

    private User userCheck(User user, Comment comment) {
        if (user.getRole().equals(Role.USER) && comment.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            return user;
        } else {
            return null;
        }
    }
    @Override
    public void getImage(Integer id, HttpServletResponse response) {
        Ad ad = null;
        try {
            ad = adRepository.findByPk(id).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (ad != null) {
            Path imagePath = Path.of(ad.getImage());
            try (
                    InputStream is = Files.newInputStream(imagePath);
                    OutputStream os = response.getOutputStream()
            ) {
                response.setContentType(Files.probeContentType(imagePath));
                response.setContentLength((int) Files.size(imagePath));
                is.transferTo(os);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}