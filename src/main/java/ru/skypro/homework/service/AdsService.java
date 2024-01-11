package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import javax.servlet.http.HttpServletResponse;

public interface AdsService {
    AdsDTO getAll();

    AdDTO addAd(CreateOrUpdateAdDTO ad, MultipartFile image);

    ExtendedAdDTO getAd(Integer pk);

    boolean deleteAd(Integer pk);

    AdDTO updateAd(Integer pk, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    AdsDTO getAllMine();

    String updateAdImage(Integer id, MultipartFile image);

    CommentsDTO getAdComments(Integer pk);

    CommentDTO addComment(Integer id, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);

    boolean deleteComment(Integer adPk, Integer commentPk);

    CommentDTO updateComment(Integer adPk, Integer commentPk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);

    void getImage(Integer id, HttpServletResponse response);
}
