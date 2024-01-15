package ru.skypro.homework.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UsersServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * A controller for users and ads image requests. <br>
 * <br>
 * <hr>
 * <br>
 * Контроллер для запросов изображений пользователей и объявлений. <br>
 * <br>
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {
    private final ImageServiceImpl imageService;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    /**
     * A method of the controller for getting {@link Array} of {@link Byte} with image of user or ad. <br>
     * Used service method {@link ImageService#getImage(String, HttpServletResponse)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения массива {@link Array} байтов {@link Byte} с изображением пользователя или объявления. <br>
     * Использован метод сервиса {@link ImageService#getImage(String, HttpServletResponse)}. <br>
     * <br>
     *
     * @param imageName
     * @param response
     * @return {@link HttpStatus#UNAUTHORIZED} or {@link HttpStatus#OK} and {@link Array} of {@link Byte}
     * @see ImageService#getImage(String, HttpServletResponse)
     */
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
