package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.service.impl.AdsServiceImpl;

@RestController
@RequestMapping(path = "/ads")
public class AdsController {
    private final AdsServiceImpl adsService;

    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    public ResponseEntity<AdsDTO> getAll() {
        if (adsService.getAll() != null) {
            return ResponseEntity.ok().body(adsService.getAll());
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> postAd(@RequestParam CreateOrUpdateAdDTO ad, @RequestBody MultipartFile image) {
        AdDTO newAdDTO = adsService.addAd(ad, image);
        return ResponseEntity.status(201).body(newAdDTO);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity updateAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMe() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}/image")
    public ResponseEntity getAdImage(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}/comments")
    public ResponseEntity getAdComments(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{id}/comments")
    public ResponseEntity postAdComment(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity deleteAdComment(@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity updateAdComment(@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }
}
