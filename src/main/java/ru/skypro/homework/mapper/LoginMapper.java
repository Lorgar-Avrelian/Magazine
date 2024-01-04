package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.Login;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginDTO loginToLoginDto(Login login);

    Login loginDtoToLogin(LoginDTO login);

    Login registerDtoToLogin(RegisterDTO register);
}
