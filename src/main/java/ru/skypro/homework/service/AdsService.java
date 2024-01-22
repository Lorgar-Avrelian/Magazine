package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.io.FileNotFoundException;

/**
 * A service for processing ads and ad commits requests. <br>
 * <br>
 * <hr>
 * <br>
 * Сервис для обработки запросов объявлений и комментариев к объявлениям. <br>
 * <br>
 */
public interface AdsService {
    /**
     * A method of the service for getting DTO with list of all ads. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения DTO со списком всех объявлений. <br>
     * <br>
     *
     * @return {@link AdsDTO}
     * @see AdsServiceImpl#getAll()
     */
    AdsDTO getAll();

    /**
     * A method of the service for processing new ad in form of DTO {@link CreateOrUpdateAdDTO} with params and {@link MultipartFile} with image. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обработки нового объявления в форме DTO {@link CreateOrUpdateAdDTO} с параметрами и {@link MultipartFile} с изображением. <br>
     * <br>
     *
     * @param adDTO
     * @param image
     * @return {@link AdDTO}
     * @see AdsServiceImpl#addAd(CreateOrUpdateAdDTO, MultipartFile)
     */
    AdDTO addAd(CreateOrUpdateAdDTO adDTO, MultipartFile image);

    /**
     * A method of the service for getting DTO {@link ExtendedAdDTO} with the ad having this id. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения DTO {@link ExtendedAdDTO} c объявлением, имеющим данный id. <br>
     * <br>
     *
     * @param pk
     * @return {@link ExtendedAdDTO}
     * @see AdsServiceImpl#getAd(Integer)
     */
    ExtendedAdDTO getAd(Integer pk);

    /**
     * A method of the service for deleting ad having this id. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для удаления объявления, имеющего данный id. <br>
     * <br>
     *
     * @param pk
     * @return {@link Boolean}
     * @see AdsServiceImpl#deleteAd(Integer)
     */
    boolean deleteAd(Integer pk);

    /**
     * A method of the service for updating params of the ad having this id by processing DTO {@link CreateOrUpdateAdDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления параметров объявления, имеющего данный id, путём обработки DTO {@link CreateOrUpdateAdDTO}. <br>
     * <br>
     *
     * @param pk
     * @param createOrUpdateAdDTO
     * @return {@link AdDTO}
     * @see AdsServiceImpl#updateAd(Integer, CreateOrUpdateAdDTO)
     */
    AdDTO updateAd(Integer pk, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    /**
     * A method of the service for getting list with all ads of authorized user in form DTO {@link AdsDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения списка со всеми объявлениями авторизованного пользователя в форме DTO {@link AdsDTO}. <br>
     * <br>
     *
     * @return {@link AdsDTO}
     * @see AdsServiceImpl#getAllMine()
     */
    AdsDTO getAllMine();

    /**
     * A method of the service for updating image of the ad having this id by sending {@link MultipartFile}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления изображения объявления, имеющего данный id, путём отправки {@link MultipartFile}. <br>
     * <br>
     *
     * @param id
     * @param image
     * @return {@link String}
     * @see AdsServiceImpl#updateAdImage(Integer, MultipartFile)
     */
    String updateAdImage(Integer id, MultipartFile image) throws FileNotFoundException;

    /**
     * A method of the service for getting list of comments to the ad having this id in form DTO {@link CommentsDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения списка комментариев к объявлению, имеющему данный id, в форме DTO {@link CommentsDTO}. <br>
     * <br>
     *
     * @param pk
     * @return {@link CommentsDTO}
     * @see AdsServiceImpl#getAdComments(Integer)
     */
    CommentsDTO getAdComments(Integer pk);

    /**
     * A method of the service for processing new comment or updating old comment to the ad having this id in form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обработки нового комментария или обновления старого комментария к объявлению, имеющему данный id, в форме DTO {@link CreateOrUpdateCommentDTO}. <br>
     * <br>
     *
     * @param id
     * @param createOrUpdateCommentDTO
     * @return {@link CommentsDTO}
     * @see AdsServiceImpl#addComment(Integer, CreateOrUpdateCommentDTO)
     */
    CommentDTO addComment(Integer id, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);

    /**
     * A method of the service for deleting comment having this comment id to the ad having this ad id. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для удаления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления. <br>
     * <br>
     *
     * @param adPk
     * @param commentPk
     * @return {@link Boolean}
     * @see AdsServiceImpl#deleteComment(Integer, Integer)
     */
    boolean deleteComment(Integer adPk, Integer commentPk);

    /**
     * A method of the service for updating comment having this comment id to the ad having this ad id by processing form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления, путём обработки DTO {@link CreateOrUpdateCommentDTO}. <br>
     * <br>
     *
     * @param adPk
     * @param commentPk
     * @param createOrUpdateCommentDTO
     * @return {@link CommentDTO}
     * @see AdsServiceImpl#updateComment(Integer, Integer, CreateOrUpdateCommentDTO)
     */
    CommentDTO updateComment(Integer adPk, Integer commentPk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
