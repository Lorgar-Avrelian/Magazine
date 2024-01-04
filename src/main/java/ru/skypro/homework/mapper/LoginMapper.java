package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;

@Mapper
public interface LoginMapper {
    Login loginToLoginDto(ru.skypro.homework.entity.Login login);

    ru.skypro.homework.entity.Login loginDtoToLogin(Login login);

    ru.skypro.homework.entity.Login registerDtoToLogin(Register register);
}
