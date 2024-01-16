package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class ImageServiceImplTest {
    @Mock
    HttpServletResponse response;
    @InjectMocks
    ImageServiceImpl imageService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getImage() throws IOException {
        File file = new File("tests/user.jpg");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile avatar = new MockMultipartFile("user_1.jpg", "user_1.jpg", "image/jpg", inputStream);
        imageService.getImage("user_1.jpg", response);
        assertTrue(Files.isSameFile(Path.of("tests/user_1.jpg"), Path.of("tests/user.jpg")));
    }
}