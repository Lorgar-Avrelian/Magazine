package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ads")
public class AdsController {
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity postImage() {
        return ResponseEntity.ok().build();
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
