package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

@Mapper
public interface UserMapper {
    User userToUserDto(ru.skypro.homework.entity.User user);

    ru.skypro.homework.entity.User userDtoToUser(User user);

    ru.skypro.homework.entity.User updateUserDtoToUser(UpdateUser updateUser);

    UpdateUser userToUpdateUserDto(ru.skypro.homework.entity.User user);
    @Mapping(target = "email", source = "register.username")
    ru.skypro.homework.entity.User registerDtoToUser(Register register);
    @Mapping(target = "firstName", source = "extendedAd.authorFirstName")
    @Mapping(target = "lastName", source = "extendedAd.authorLastName")
    @Mapping(target = "image", ignore = true)
    ru.skypro.homework.entity.User extendedAdToUser(ExtendedAd extendedAd);
}
