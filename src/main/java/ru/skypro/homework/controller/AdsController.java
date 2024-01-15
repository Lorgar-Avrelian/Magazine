package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import javax.validation.Valid;

/**
 * A controller for ads and ad commits requests. <br>
 * <br>
 * <hr>
 * <br>
 * Контроллер для запросов объявлений и комментариев к объявлениям. <br>
 * <br>
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(path = "/ads")
public class AdsController {
    private final AdsServiceImpl adsService;

    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
    }

    /**
     * A method of the controller for getting DTO with list of all ads. <br>
     * Used service method {@link AdsService#getAll()}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения DTO со списком всех объявлений. <br>
     * Использован метод сервиса {@link AdsService#getAll()}. <br>
     * <br>
     *
     * @return {@link HttpStatus#OK} and {@link AdsDTO}
     * @see AdsService#getAll()
     */
    @GetMapping
    public ResponseEntity<AdsDTO> getAll() {
        return ResponseEntity.ok().body(adsService.getAll());
    }

    /**
     * A method of the controller for posting new ad in form of DTO {@link CreateOrUpdateAdDTO} with params and {@link MultipartFile} with image. <br>
     * Used service method {@link AdsService#addAd(CreateOrUpdateAdDTO, MultipartFile)} and annotation {@link Valid} for validation of the parts of request. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для отправки нового объявления в форме DTO {@link CreateOrUpdateAdDTO} с параметрами и {@link MultipartFile} с изображением. <br>
     * Использованы метод сервиса {@link AdsService#addAd(CreateOrUpdateAdDTO, MultipartFile)} и аннотация {@link Valid} для валидации частей запроса. <br>
     * <br>
     *
     * @param ad
     * @param image
     * @return {@link HttpStatus#CREATED} and {@link AdDTO}
     * @see AdsService#addAd(CreateOrUpdateAdDTO, MultipartFile)
     * @see Valid
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AdDTO> postAd(@RequestPart("properties") @Valid CreateOrUpdateAdDTO ad, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.status(201).body(adsService.addAd(ad, image));
    }

    /**
     * A method of the controller for getting DTO {@link ExtendedAdDTO} with the ad having this id. <br>
     * Used service method {@link AdsService#getAd(Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения DTO {@link ExtendedAdDTO} c объявлением, имеющим данный id. <br>
     * Использован метод сервиса {@link AdsService#getAd(Integer)}. <br>
     * <br>
     *
     * @param id
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link ExtendedAdDTO}
     * @see AdsService#getAd(Integer)
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ExtendedAdDTO> getAd(@PathVariable Integer id) {
        ExtendedAdDTO extendedAdDTO = adsService.getAd(id);
        if (extendedAdDTO != null) {
            return ResponseEntity.status(200).body(extendedAdDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for deleting ad having this id. <br>
     * Used service method {@link AdsService#deleteAd(Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для удаления объявления, имеющего данный id. <br>
     * Использован метод сервиса {@link AdsService#deleteAd(Integer)}. <br>
     * <br>
     *
     * @param id
     * @return {@link HttpStatus#NO_CONTENT} or {@link HttpStatus#NOT_FOUND}
     * @see AdsService#deleteAd(Integer)
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (adsService.deleteAd(id)) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for updating params of the ad having this id by sending DTO {@link CreateOrUpdateAdDTO}. <br>
     * Used service method {@link AdsService#updateAd(Integer, CreateOrUpdateAdDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для обновления параметров объявления, имеющего данный id, путём отправки DTO {@link CreateOrUpdateAdDTO}. <br>
     * Использован метод сервиса {@link AdsService#updateAd(Integer, CreateOrUpdateAdDTO)}. <br>
     * <br>
     *
     * @param id
     * @param createOrUpdateAdDTO
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link AdDTO}
     * @see AdsService#updateAd(Integer, CreateOrUpdateAdDTO)
     */
    @PatchMapping(path = "/{id}")
    public ResponseEntity<AdDTO> updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        AdDTO adDTO = adsService.updateAd(id, createOrUpdateAdDTO);
        if (adDTO == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok(adDTO);
        }
    }

    /**
     * A method of the controller for getting list with all ads of authorized user in form DTO {@link AdsDTO}. <br>
     * Used service method {@link AdsService#getAllMine()}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения списка со всеми объявлениями авторизованного пользователя в форме DTO {@link AdsDTO}. <br>
     * Использован метод сервиса {@link AdsService#getAllMine()}. <br>
     * <br>
     *
     * @return {@link HttpStatus#OK} and {@link AdsDTO}
     * @see AdsService#getAllMine()
     */
    @GetMapping(path = "/me")
    public ResponseEntity<AdsDTO> getMe() {
        return ResponseEntity.ok(adsService.getAllMine());
    }

    /**
     * A method of the controller for updating image of the ad having this id by sending {@link MultipartFile}. <br>
     * Used service method {@link AdsService#updateAdImage(Integer, MultipartFile)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для обновления изображения объявления, имеющего данный id, путём отправки {@link MultipartFile}. <br>
     * Использован метод сервиса {@link AdsService#updateAdImage(Integer, MultipartFile)}. <br>
     * <br>
     *
     * @param id
     * @param image
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link String}
     * @see AdsService#updateAdImage(Integer, MultipartFile)
     */
    @PatchMapping(path = "/{id}/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> getAdImage(@RequestPart @Valid Integer id, @RequestBody MultipartFile image) {
        String imageUrl = adsService.updateAdImage(id, image);
        if (imageUrl != null) {
            return ResponseEntity.ok(imageUrl);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for getting list of comments to the ad having this id in form DTO {@link CommentsDTO}. <br>
     * Used service method {@link AdsService#getAdComments(Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения списка комментариев к объявлению, имеющему данный id, в форме DTO {@link CommentsDTO}. <br>
     * Использован метод сервиса {@link AdsService#getAdComments(Integer)}. <br>
     * <br>
     *
     * @param id
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link CommentsDTO}
     * @see AdsService#getAdComments(Integer)
     */
    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<CommentsDTO> getAdComments(@PathVariable Integer id) {
        CommentsDTO commentsDTO = adsService.getAdComments(id);
        if (commentsDTO != null) {
            return ResponseEntity.ok(commentsDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for posting new comment or updating old comment to the ad having this id in form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Used service method {@link AdsService#addComment(Integer, CreateOrUpdateCommentDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для отправки нового комментария или обновления старого комментария к объявлению, имеющему данный id, в форме DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Использован метод сервиса {@link AdsService#addComment(Integer, CreateOrUpdateCommentDTO)}. <br>
     * <br>
     *
     * @param id
     * @param createOrUpdateCommentDTO
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link CommentsDTO}
     * @see AdsService#addComment(Integer, CreateOrUpdateCommentDTO)
     */
    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<CommentDTO> postAdComment(@PathVariable Integer id, @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        CommentDTO commentDTO = adsService.addComment(id, createOrUpdateCommentDTO);
        if (commentDTO != null) {
            return ResponseEntity.ok(commentDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for deleting comment having this comment id to the ad having this ad id. <br>
     * Used service method {@link AdsService#deleteComment(Integer, Integer)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для удаления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления. <br>
     * Использован метод сервиса {@link AdsService#deleteComment(Integer, Integer)}. <br>
     * <br>
     *
     * @param adId
     * @param commentId
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK}
     * @see AdsService#deleteComment(Integer, Integer)
     */
    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteAdComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        if (adsService.deleteComment(adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * A method of the controller for updating comment having this comment id to the ad having this ad id by sending form DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Used service method {@link AdsService#updateComment(Integer, Integer, CreateOrUpdateCommentDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для обновления комментария, имеющего данный id комментария, к объявлению, имеющему данный id объявления, путём отправки DTO {@link CreateOrUpdateCommentDTO}. <br>
     * Использован метод сервиса {@link AdsService#updateComment(Integer, Integer, CreateOrUpdateCommentDTO)}. <br>
     * <br>
     *
     * @param adId
     * @param commentId
     * @param createOrUpdateCommentDTO
     * @return {@link HttpStatus#NOT_FOUND} or {@link HttpStatus#OK} and {@link CommentDTO}
     * @see AdsService#updateComment(Integer, Integer, CreateOrUpdateCommentDTO)
     */
    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateAdComment(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        CommentDTO commentDTO = adsService.updateComment(adId, commentId, createOrUpdateCommentDTO);
        if (commentDTO != null) {
            return ResponseEntity.ok(commentDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}