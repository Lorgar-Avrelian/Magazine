package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO user);

    User updateUserDtoToUser(UpdateUserDTO updateUser);

    UpdateUserDTO userToUpdateUserDto(User user);

    @Mapping(target = "email", source = "register.username")
    User registerDtoToUser(RegisterDTO register);

    @Mapping(target = "firstName", source = "extendedAd.authorFirstName")
    @Mapping(target = "lastName", source = "extendedAd.authorLastName")
    @Mapping(target = "image", ignore = true)
    User extendedAdToUser(ExtendedAdDTO extendedAd);

    @Mapping(target = "username", source = "user.email")
    LoginDTO userToLoginDto(User user);

    @Mapping(target = "email", source = "loginDTO.username")
    User loginDtoToUser(LoginDTO loginDTO);
}
