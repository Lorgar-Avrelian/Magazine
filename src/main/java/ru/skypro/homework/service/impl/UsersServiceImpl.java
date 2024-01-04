package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.Login;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.LoginRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
public class UsersServiceImpl implements UsersService {
    @Value("${user.image.dir.path}")
    private String imageDir;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final UserMapper userMapper;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UserRepository userRepository, LoginRepository loginRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
        this.userMapper = userMapper;
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
            Login login = loginRepository.findByUsername(user.getEmail());
            if (login.getPassword().equals(newPassword.getCurrentPassword())) {
                login.setPassword(newPassword.getNewPassword());
                loginRepository.save(login);
            }
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
    public UpdateUserDTO getUser(UpdateUserDTO updateUserDto) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        UpdateUserDTO updateUser;
        if (user != null) {
            updateUser = userMapper.userToUpdateUserDto(user);
        } else {
            return null;
        }
        return updateUser;
    }

    @Override
    public void setImage(MultipartFile image) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        Path filePath = null;
//        try {
//            filePath = Path.of(imageDir, user.getId() + "." + getExtension(image.getOriginalFilename()));
//            Files.createDirectories(filePath.getParent());
//            Files.deleteIfExists(filePath);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//        try (
//                InputStream is = image.getInputStream();
//                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
//        ) {
//            bis.transferTo(bos);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//        Avatar avatar = getAvatar(id);
//        avatar.setStudent(student);
//        avatar.setFilePath(filePath.toString());
//        avatar.setFileSize(file.getSize());
//        avatar.setMediaType(file.getContentType());
//        avatar.setData(generateImagePreview(filePath));
//        avatarRepository.save(avatar);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
