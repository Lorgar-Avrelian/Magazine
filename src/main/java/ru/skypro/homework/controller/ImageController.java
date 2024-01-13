package ru.skypro.homework.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UsersServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {
    private final ImageServiceImpl imageService;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping(path = "/{imageName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> downloadImage(@PathVariable String imageName, HttpServletResponse response) {
        try {
            return imageService.getImage(imageName, response);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
}
