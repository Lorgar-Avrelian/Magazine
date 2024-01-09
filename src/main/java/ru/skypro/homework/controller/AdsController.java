package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(path = "/ads")
public class AdsController {
    private final AdsServiceImpl adsService;

    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    public ResponseEntity<AdsDTO> getAll() {
        return ResponseEntity.ok().body(adsService.getAll());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AdDTO> postAd(@RequestPart("properties") @Valid CreateOrUpdateAdDTO ad, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.status(201).body(adsService.addAd(ad, image));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExtendedAdDTO> getAd(@PathVariable Integer id) {
        ExtendedAdDTO extendedAdDTO = adsService.getAd(id);
        if (extendedAdDTO != null) {
            return ResponseEntity.status(200).body(extendedAdDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (adsService.deleteAd(id)) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AdDTO> updateAd(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        AdDTO adDTO = adsService.updateAd(id, createOrUpdateAdDTO);
        if (adDTO == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok(adDTO);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<AdsDTO> getMe() {
        return ResponseEntity.ok(adsService.getAllMine());
    }

    @PatchMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> getAdImage(@PathVariable Integer id, @RequestBody MultipartFile image) {
        String imageUrl = adsService.updateAdImage(id, image);
        if (imageUrl != null) {
            return ResponseEntity.ok(imageUrl);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<CommentsDTO> getAdComments(@PathVariable Integer id) {
        CommentsDTO commentsDTO = adsService.getAdComments(id);
        if (commentsDTO != null) {
            return ResponseEntity.ok(commentsDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<CommentDTO> postAdComment(@PathVariable Integer id, @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        CommentDTO commentDTO = adsService.addComment(id, createOrUpdateCommentDTO);
        if (commentDTO != null) {
            return ResponseEntity.ok(commentDTO);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteAdComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        if (adsService.deleteComment(adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

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
