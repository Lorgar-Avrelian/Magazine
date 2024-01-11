package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Value("${user.image.dir.path}")
    private String imageDir;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void setPassword(NewPasswordDTO newPassword) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public UserDTO getUser() {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        UserDTO userDto;
        if (user != null) {
            userDto = userMapper.userToUserDto(user);
        } else {
            return null;
        }
        return userDto;
    }

    @Override
    public UpdateUserDTO updateUser(UpdateUserDTO updateUserDto) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            User userUpdate = userMapper.updateUserDtoToUser(updateUserDto);
            user.setFirstName(userUpdate.getFirstName());
            user.setLastName(userUpdate.getLastName());
            user.setPhone(userUpdate.getPhone());
            userRepository.save(user);
        } else {
            return null;
        }
        return userMapper.userToUpdateUserDto(user);
    }

    @Override
    public void setImage(MultipartFile image) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            Path filePath = null;
            try {
                filePath = Path.of(imageDir, "user_" + user.getId() + "." + getExtension(image.getOriginalFilename()));
                Files.createDirectories(filePath.getParent());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            try (
                    InputStream is = image.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            user.setImage(String.valueOf(filePath));
            userRepository.save(user);
        }
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @Override
    public void getImage(Integer id, HttpServletResponse response) {
        User user = null;
        try {
            user = userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            Path imagePath = Path.of(user.getImage());
            try (
                    InputStream is = Files.newInputStream(imagePath);
                    OutputStream os = response.getOutputStream()
            ) {
                response.setContentType(Files.probeContentType(imagePath));
                response.setContentLength((int) Files.size(imagePath));
                is.transferTo(os);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
