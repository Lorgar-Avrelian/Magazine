package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;

public interface UsersService {
    void setPassword(NewPasswordDTO newPassword);

    UserDTO getUser();

    UpdateUserDTO getUser(UpdateUserDTO updateUserDto);

    void setImage(MultipartFile image);
}
