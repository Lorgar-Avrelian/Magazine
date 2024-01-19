package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * An implementation of the service for processing ads and ad commits requests {@link UsersService}. <br>
 * <br>
 * <hr>
 * <br>
 * Реализация сервиса для обработки запросов объявлений и комментариев к объявлениям {@link UsersService}. <br>
 * <br>
 *
 * @see UsersService
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Value("${user.image.dir.path}")
    private String imageDir;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoderConfig passwordEncoderConfig) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    /**
     * A method of the service for changing password of the user by processing DTO {@link NewPasswordDTO}. <br>
     * Used repository methods {@link UserRepository#findByEmail(String)} and {@link UserRepository#save(Object)} and {@link PasswordEncoder} method {@link PasswordEncoder#encode(CharSequence)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для изменения пароля пользователя путём обработки DTO {@link NewPasswordDTO}. <br>
     * Использованы методы репозитория {@link UserRepository#findByEmail(String)} и {@link UserRepository#save(Object)} и {@link PasswordEncoder} метод {@link PasswordEncoder#encode(CharSequence)}. <br>
     * <br>
     *
     * @param newPassword
     * @see UserRepository#findByEmail(String)
     * @see UserRepository#save(Object)
     * @see PasswordEncoder#encode(CharSequence)
     */
    @Override
    public void setPassword(NewPasswordDTO newPassword) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            user.setPassword(passwordEncoderConfig.passwordEncoder().encode(newPassword.getNewPassword()));
            userRepository.save(user);
        }
    }

    /**
     * A method of the service for getting info about user in form of DTO {@link UserDTO}. <br>
     * Used repository method {@link UserRepository#findByEmail(String)} and {@link Mapper} method {@link UserMapper#userToUserDto(User)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения информации о пользователе в форме DTO {@link UserDTO}. <br>
     * Использованы метод репозитория {@link UserRepository#findByEmail(String)} и {@link Mapper} метод {@link UserMapper#userToUserDto(User)}. <br>
     * <br>
     *
     * @return {@link UserDTO}
     * @see UserRepository#findByEmail(String)
     * @see UserMapper#userToUserDto(User)
     */
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

    /**
     * A method of the service for updating info about user by processing DTO {@link UpdateUserDTO}. <br>
     * Used repository methods {@link UserRepository#findByEmail(String)} and {@link UserRepository#save(Object)}, {@link Mapper} methods {@link UserMapper#updateUserDtoToUser(UpdateUserDTO)} and {@link UserMapper#userToUpdateUserDto(User)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления информации о пользователе путём обработки DTO {@link UpdateUserDTO}. <br>
     * Использованы методы репозитория {@link UserRepository#findByEmail(String)} и {@link UserRepository#save(Object)}, {@link Mapper} методы {@link UserMapper#updateUserDtoToUser(UpdateUserDTO)} и {@link UserMapper#userToUpdateUserDto(User)}. <br>
     * <br>
     *
     * @param updateUserDto
     * @return {@link UpdateUserDTO}
     * @see UserRepository#findByEmail(String)
     * @see UserRepository#save(Object)
     * @see UserMapper#updateUserDtoToUser(UpdateUserDTO)
     * @see UserMapper#userToUpdateUserDto(User)
     */
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

    /**
     * A method of the service for updating user image by processing {@link MultipartFile}. <br>
     * Used repository methods {@link UserRepository#findByEmail(String)} and {@link UserRepository#save(Object)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления изображения пользователе путём обработки {@link MultipartFile}. <br>
     * Использованы методы репозитория {@link UserRepository#findByEmail(String)} и {@link UserRepository#save(Object)}. <br>
     * <br>
     *
     * @param image
     * @see UserRepository#findByEmail(String)
     * @see UserRepository#save(Object)
     */
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
            String fileName = "user_" + user.getId() + "." + getExtension(image.getOriginalFilename());
            try {
                filePath = Path.of(imageDir, fileName);
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
            user.setImage(fileName);
            userRepository.save(user);
        }
    }

    /**
     * An auxiliary method for getting username (email) of authorized {@link User} from {@link SecurityContextHolder#getContext()}. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения username (email) авторизованного {@link User} из {@link SecurityContextHolder#getContext()}. <br>
     * <br>
     *
     * @return {@link String} or null
     */
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * An auxiliary method for getting file extension from filename. <br>
     * <br>
     * <hr>
     * <br>
     * Вспомогательный метод для получения расширения файла из его названия. <br>
     * <br>
     *
     * @return {@link String} or null
     */
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
