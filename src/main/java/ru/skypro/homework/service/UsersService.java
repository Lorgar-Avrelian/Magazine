package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;

public interface UsersService {
    void setPassword(NewPasswordDTO newPassword);

    UserDTO getUser();

    UpdateUserDTO updateUser(UpdateUserDTO updateUserDto);

    void setImage(MultipartFile image);

    void getImage(Integer id, HttpServletResponse response);
}
