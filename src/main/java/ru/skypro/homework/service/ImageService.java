package ru.skypro.homework.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * A service for processing images requests. <br>
 * <br>
 * <hr>
 * <br>
 * Сервис для обработки запросов изображений. <br>
 * <br>
 */
public interface ImageService {
    /**
     * A method of the service for getting {@link Array} of {@link Byte} with image of user or ad. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения массива {@link Array} байтов {@link Byte} с изображением пользователя или объявления. <br>
     * <br>
     *
     * @param imageName
     * @param response
     * @return {@link HttpStatus#OK} and {@link Array} of {@link Byte}
     * @see ImageServiceImpl#getImage(String, HttpServletResponse)
     */
    ResponseEntity<?> getImage(String imageName, HttpServletResponse response) throws IOException;
}
