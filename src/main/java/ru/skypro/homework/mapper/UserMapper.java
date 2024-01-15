package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;

/**
 * {@link Mapper} that converts DAO {@link User} to DTO's {@link UserDTO}, {@link UpdateUserDTO}, {@link RegisterDTO}, {@link ExtendedAdDTO} and {@link LoginDTO}, and vice versa. <br>
 * <br>
 * <hr>
 * <br>
 * {@link Mapper}, который конвертирует DAO {@link User} в DTO {@link UserDTO}, {@link UpdateUserDTO}, {@link RegisterDTO}, {@link ExtendedAdDTO} и {@link LoginDTO}, и наоборот. <br>
 * <br>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserDTO userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        if (user.getId() != null) {
            userDTO.setId(user.getId());
        }
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setImage("/" + user.getImage());
        return userDTO;
    }

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
