package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.impl.UsersServiceImpl;

/**
 * A service for processing requests about users info. <br>
 * <br>
 * <hr>
 * <br>
 * Сервис для обработки запросов, связанных с информацией о пользователях. <br>
 * <br>
 */
public interface UsersService {
    /**
     * A method of the service for changing password of the user by processing DTO {@link NewPasswordDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для изменения пароля пользователя путём обработки DTO {@link NewPasswordDTO}. <br>
     * <br>
     *
     * @param newPassword
     * @see UsersServiceImpl#setPassword(NewPasswordDTO)
     */
    void setPassword(NewPasswordDTO newPassword);

    /**
     * A method of the service for getting info about user in form of DTO {@link UserDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для получения информации о пользователе в форме DTO {@link UserDTO}. <br>
     * <br>
     *
     * @return {@link UserDTO}
     * @see UsersServiceImpl#getUser()
     */
    UserDTO getUser();

    /**
     * A method of the service for updating info about user by processing DTO {@link UpdateUserDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления информации о пользователе путём обработки DTO {@link UpdateUserDTO}. <br>
     * <br>
     *
     * @param updateUserDto
     * @return {@link UpdateUserDTO}
     * @see UsersService#updateUser(UpdateUserDTO)
     */
    UpdateUserDTO updateUser(UpdateUserDTO updateUserDto);

    /**
     * A method of the service for updating user image by processing {@link MultipartFile}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для обновления изображения пользователе путём обработки {@link MultipartFile}. <br>
     * <br>
     *
     * @param image
     * @see UsersService#setImage(MultipartFile)
     */
    void setImage(MultipartFile image);
}
