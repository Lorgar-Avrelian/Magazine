package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ImageService {
    ResponseEntity<?> getImage(String imageName, HttpServletResponse response) throws IOException;
}
