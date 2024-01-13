package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${user.image.dir.path}")
    private String userImageDir;
    @Value("${ad.image.dir.path}")
    private String addsImageDir;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    @Override
    public ResponseEntity<byte[]> getImage(String imageName, HttpServletResponse response) throws IOException {
        Path imagePath = Path.of(userImageDir, imageName);
        if (!Files.isExecutable(imagePath)) {
            imagePath = Path.of(addsImageDir, imageName);
        }
        if (!Files.isExecutable(imagePath)) {
            throw new IOException("File doesn't exist");
        }
        Tika tika = new Tika();
        String mimeType = tika.detect(imagePath);
        MediaType mediaType = MediaType.parseMediaType(mimeType);
        byte[] imageInBytes = new byte[(int) Files.size(imagePath)];
        try (
                InputStream is = Files.newInputStream(imagePath)
        ) {
            IOUtils.readFully(is, imageInBytes);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentLength(imageInBytes.length);
        headers.setContentDispositionFormData(imageName, imageName);
        return ResponseEntity.ok().headers(headers).body(imageInBytes);
    }
}
