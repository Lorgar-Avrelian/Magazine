package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.ClockConfig;
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
import java.util.List;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * An implementation of the service for processing ads and ad commits requests {@link AdsService}. <br>
 * <br>
 * <hr>
 * <br>
 * Реализация сервиса для обработки запросов объявлений и комментариев к объявлениям {@link AdsService}. <br>
 * <br>
 *
 * @see AdsService
 */
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
    private final ClockConfig clock;
    private static final Logger log = Logger.getLogger(AdsServiceImpl.class);

    public AdsServiceImpl(AdRepository adRepository, CommentRepository commentRepository, UserRepository userRepository, AdMapper adMapper, CommentMapper commentMapper, ClockConfig clock) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.commentMapper = commentMapper;
        this.clock = clock;
    }

    /**
     * A method of the service for getting DTO with list of all ads. <br>
     * Used repository method {@link AdRepository#findAll()} and {@link Mapper} method {@link AdMapper#adsListToAdsDto(List)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения DTO со списком всех объявлений. <br>
     * Использованы метод репозитория {@link AdRepository#findAll()} и {@link Mapper} метод {@link AdMapper#adsListToAdsDto(List)}. <br>
     * <br>
     *
     * @return {@link AdsDTO}
     * @see AdRepository#findAll()
     * @see AdMapper#adsListToAdsDto(List)
     */
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

    /**
     * A method of the service for processing new ad in form of DTO {@link CreateOrUpdateAdDTO} with params and {@link MultipartFile} with image. <br>
     * Used repository method {@link AdRepository#save(Object)} and {@link Mapper} methods {@link AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)} and {@link AdMapper#adToAdDto(Ad)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обработки нового объявления в форме DTO {@link CreateOrUpdateAdDTO} с параметрами и {@link MultipartFile} с изображением. <br>
     * Использованы метод репозитория {@link AdRepository#save(Object)} и {@link Mapper} методы {@link AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)} и {@link AdMapper#adToAdDto(Ad)}. <br>
     * <br>
     *
     * @param adDTO
     * @param image
     * @return {@link AdDTO}
     * @see AdRepository#save(Object)
     * @see AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)
     * @see AdMapper#adToAdDto(Ad)
     */
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
        String fileName = "user_" + user.getId() + "_ad_" + ad.getPk() + "." + getExtension(image.getOriginalFilename());
        try {
            filePath = Path.of(imageDir, fileName);
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
        ad.setImage(fileName);
        ad = adRepository.save(ad);
        return adMapper.adToAdDto(ad);
    }

    /**
     * An auxiliary method for getting username (email) of authorized {@link User} from {@link SecurityContextHolder#getContext()}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения username (email) авторизованного {@link User} из {@link SecurityContextHolder#getContext()}. <br>
     * <br>
     *
     * @return {@link String} or null
     */
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * An auxiliary method for getting file extension from filename. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения расширения файла из его названия. <br>
     * <br>
     *
     * @return {@link String} or null
     */
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * A method of the service for getting DTO {@link ExtendedAdDTO} with the ad having this id. <br>
     * Used repository method {@link AdRepository#findByPk(Integer)} and {@link Mapper} method {@link AdMapper#adToExtendedAd(Ad, User)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения DTO {@link ExtendedAdDTO} c объявлением, имеющим данный id. <br>
     * Использованы метод репозитория {@link AdRepository#findByPk(Integer)} и {@link Mapper} метод {@link AdMapper#adToExtendedAd(Ad, User)}. <br>
     * <br>
     *
     * @param pk
     * @return {@link ExtendedAdDTO}
     * @see AdRepository#findByPk(Integer)
     * @see AdMapper#adToExtendedAd(Ad, User)
     */
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

    /**
     * A method of the service for deleting ad having this id. <br>
     * Used repository method {@link AdRepository#delete(Object)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для удаления объявления, имеющего данный id. <br>
     * Использован метод репозитория {@link AdRepository#delete(Object)}. <br>
     * <br>
     *
     * @param pk
     * @return {@link Boolean}
     * @see AdRepository#delete(Object)
     */
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
            throw new UsernameNotFoundException("User doesn't have such rights!");
        }
        try {
            adRepository.delete(ad);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * A method of the service for updating params of the ad having this id by processing DTO {@link CreateOrUpdateAdDTO}. <br>
     * Used repository method {@link AdRepository#save(Object)} and {@link Mapper} methods {@link AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)} and {@link AdMapper#adToAdDto(Ad)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления параметров объявления, имеющего данный id, путём обработки DTO {@link CreateOrUpdateAdDTO}. <br>
     * Использованы метод репозитория {@link AdRepository#save(Object)} и {@link Mapper} методы {@link AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)} и {@link AdMapper#adToAdDto(Ad)}. <br>
     * <br>
     *
     * @param pk
     * @param createOrUpdateAdDTO
     * @return {@link AdDTO}
     * @see AdRepository#save(Object)
     * @see AdMapper#createOrUpdateAdDtoToAd(CreateOrUpdateAdDTO)
     */
    @Override
    public AdDTO updateAd(Integer pk, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        User user = getUser();
        Ad adUpdate = adMapper.createOrUpdateAdDtoToAd(createOrUpdateAdDTO);
        Ad ad = getThatAd(pk);
        if (user != null && ad != null) {
            user = userCheck(user, ad);
        } else {
            return null;
        }
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't have such rights!");
        }
        ad.setTitle(adUpdate.getTitle());
        ad.setPrice(adUpdate.getPrice());
        ad.setDescription(adUpdate.getDescription());
        ad = adRepository.save(ad);
        return adMapper.adToAdDto(ad);
    }

    /**
     * A method of the service for getting list with all ads of authorized user in form DTO {@link AdsDTO}. <br>
     * Used repository method {@link AdRepository#findByAuthor(User)} and {@link Mapper} method {@link AdMapper#adsListToAdsDto(List)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения списка со всеми объявлениями авторизованного пользователя в форме DTO {@link AdsDTO}. <br>
     * Использованы метод репозитория {@link AdRepository#findByAuthor(User)} и {@link Mapper} метод {@link AdMapper#adsListToAdsDto(List)}. <br>
     * <br>
     *
     * @return {@link AdsDTO}
     * @see AdRepository#findByAuthor(User)
     * @see AdMapper#adsListToAdsDto(List)
     */
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

    /**
     * A method of the service for updating image of the ad having this id by sending {@link MultipartFile}. <br>
     * Used repository method {@link AdRepository#save(Object)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления изображения объявления, имеющего данный id, путём отправки {@link MultipartFile}. <br>
     * Использован метод репозитория {@link  AdRepository#save(Object)}. <br>
     * <br>
     *
     * @param pk
     * @param image
     * @return {@link String}
     * @see AdRepository#save(Object)
     */
    @Override
    public String updateAdImage(Integer pk, MultipartFile image) throws FileNotFoundException {
        User user = getUser();
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't have such rights!");
        }
        Ad ad = getThatAd(pk);
        if (ad == null) {
            throw new FileNotFoundException("File does not exist!");
        }
        user = userCheck(user, ad);
        if (user == null) {
            return null;
        }
        Path filePath = null;
        String fileName = "user_" + ad.getAuthor().getId() + "_ad_" + ad.getPk() + "." + getExtension(image.getOriginalFilename());
        try {
            filePath = Path.of(imageDir, fileName);
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
            return null;
        }
        ad.setImage(fileName);
        adRepository.save(ad);
        return String.valueOf(filePath);
    }

    /**
     * A method of the service for getting list of comments to the ad having this id in form DTO {@link CommentsDTO}. <br>
     * Used repository method {@link CommentRepository#findByPk(Integer)} and {@link Mapper} method {@link CommentMapper#commentListToCommentsDto(List)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения списка комментариев к объявлению, имеющему данный id, в форме DTO {@link CommentsDTO}. <br>
     * Использованы метод репозитория {@link CommentRepository#findByPk(Integer)} и {@link Mapper} метод {@link CommentMapper#commentListToCommentsDto(List)}. <br>
     * <br>
     *
     * @param pk
     * @return {@link CommentsDTO}
     * @see CommentRepository#findByPk(Integer)
     * @see CommentMapper#commentListToCommentsDto(List)
     */
    @Override
    public CommentsDTO getAdComments(Integer pk) {
        Ad ad = getThatAd(pk);
        if (ad == null) {
            return new CommentsDTO();
        }
        List<Comment> commentsList = commentRepository.findByAd(ad);
        if (commentsList.isEmpty()) {
            return null;
        } else {
            return commentMapper.commentListToCommentsDto(commentsList);
        }
    }

    /**
     * A method of the service for processing new comment or updating old comment to the ad having this id in form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Used repository method {@link CommentRepository#save(Object)} and {@link Mapper} method {@link CommentMapper#commentToCommentDto(Comment)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обработки нового комментария или обновления старого комментария к объявлению, имеющему данный id, в форме DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Использованы метод репозитория {@link CommentRepository#save(Object)} и {@link Mapper} метод {@link CommentMapper#commentToCommentDto(Comment)}. <br>
     * <br>
     *
     * @param pk
     * @param createOrUpdateCommentDTO
     * @return {@link CommentsDTO}
     * @see CommentRepository#save(Object)
     * @see CommentMapper#commentToCommentDto(Comment)
     */
    @Override
    public CommentDTO addComment(Integer pk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = getUser();
        Ad ad = getThatAd(pk);
        if (user == null || ad == null) {
            return null;
        }
        Comment comment = new Comment(null, user, user.getImage(), user.getFirstName(), clock.clock().millis(), createOrUpdateCommentDTO.getText(), ad);
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    /**
     * A method of the service for deleting comment having this comment id to the ad having this ad id. <br>
     * Used repository method {@link CommentRepository#delete(Object)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для удаления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления. <br>
     * Использован метод репозитория {@link CommentRepository#delete(Object)}. <br>
     * <br>
     *
     * @param adPk
     * @param commentPk
     * @return {@link Boolean}
     * @see CommentRepository#delete(Object)
     */
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
            throw new UsernameNotFoundException("User doesn't have such rights!");
        }
        if (comment.getAd().equals(ad)) {
            commentRepository.delete(comment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method of the service for updating comment having this comment id to the ad having this ad id by processing form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Used repository methods {@link CommentRepository#findByPk(Integer)} and {@link CommentRepository#save(Object)} and {@link Mapper} method {@link CommentMapper#commentToCommentDto(Comment)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления, путём обработки DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Использованы методы репозитория {@link CommentRepository#findByPk(Integer)} и {@link CommentRepository#save(Object)} и {@link Mapper} метод {@link CommentMapper#commentToCommentDto(Comment)}. <br>
     * <br>
     *
     * @param adPk
     * @param commentPk
     * @param createOrUpdateCommentDTO
     * @return {@link CommentDTO}
     * @see CommentRepository#findByPk(Integer)
     * @see CommentRepository#save(Object)
     * @see CommentMapper#commentToCommentDto(Comment)
     */
    @Override
    public CommentDTO updateComment(Integer adPk, Integer commentPk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User user = getUser();
        Ad ad = getThatAd(adPk);
        if (user == null || ad == null) {
            return null;
        }
        Comment comment = getComment(commentPk);
        if (comment == null || !comment.getAd().equals(ad)) {
            return null;
        } else {
            user = userCheck(user, comment);
            if (user == null) {
                throw new UsernameNotFoundException("User doesn't have such rights!");
            }
            comment.setText(createOrUpdateCommentDTO.getText());
        }
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    /**
     * An auxiliary method for getting entity of authorized {@link User} from DB. <br>
     * Used repository method {@link UserRepository#findByEmail(String)}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения сущности авторизованного {@link User} из БД. <br>
     * Использован методы репозитория {@link UserRepository#findByEmail(String)}. <br>
     * <br>
     *
     * @return {@link User} or null
     * @see UserRepository#findByEmail(String)
     */
    private User getUser() {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return user;
    }

    /**
     * An auxiliary method for getting entity {@link Ad} from DB by it's id. <br>
     * Used repository method {@link AdRepository#findByPk(Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения сущности объявления {@link Ad} из БД по его id. <br>
     * Использован методы репозитория {@link AdRepository#findByPk(Integer)}. <br>
     * <br>
     *
     * @return {@link Ad} or null
     * @see AdRepository#findByPk(Integer)
     */
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

    /**
     * An auxiliary method for getting entity {@link Comment} from DB by it's id. <br>
     * Used repository method {@link CommentRepository#findByPk(Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения сущности объявления {@link Comment} из БД по его id. <br>
     * Использован методы репозитория {@link CommentRepository#findByPk(Integer)}. <br>
     * <br>
     *
     * @return {@link Comment} or null
     * @see CommentRepository#findByPk(Integer)
     */
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

    /**
     * An auxiliary method for check that authorized {@link User} is owner of {@link Ad} or has {@link Role#ADMIN}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для проверки, что авторизованный {@link User} является автором {@link Ad} или имеет {@link Role#ADMIN}. <br>
     * <br>
     *
     * @return {@link User} or null
     */
    private User userCheck(User user, Ad ad) {
        if (user.getRole().equals(Role.USER) && ad.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * An auxiliary method for check that authorized {@link User} is owner of {@link Comment} or has {@link Role#ADMIN}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для проверки, что авторизованный {@link User} является автором {@link Comment} или имеет {@link Role#ADMIN}. <br>
     * <br>
     *
     * @return {@link User} or null
     */
    private User userCheck(User user, Comment comment) {
        if (user.getRole().equals(Role.USER) && comment.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            return user;
        } else {
            return null;
        }
    }
}