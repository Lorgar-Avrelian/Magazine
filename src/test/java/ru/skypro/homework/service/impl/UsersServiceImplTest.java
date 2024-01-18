package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.constants.Constants.USER;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersServiceImplTest {
    private NewPasswordDTO NEW_PASSWORD_USER_DTO = new NewPasswordDTO();
    private UpdateUserDTO UPDATE_USER_DTO = new UpdateUserDTO();
    private UserDTO USER_DTO = new UserDTO();
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        usersService = new UsersServiceImpl(userRepository, userMapper, passwordEncoder);
        NEW_PASSWORD_USER_DTO.setCurrentPassword(USER.getPassword());
        NEW_PASSWORD_USER_DTO.setNewPassword(USER.getPassword());
        UPDATE_USER_DTO.setFirstName(USER.getFirstName());
        UPDATE_USER_DTO.setLastName(USER.getLastName());
        UPDATE_USER_DTO.setPhone(USER.getPhone());
        USER_DTO.setId(USER.getId());
        USER_DTO.setEmail(USER.getEmail());
        USER_DTO.setFirstName(USER.getFirstName());
        USER_DTO.setLastName(USER.getLastName());
        USER_DTO.setPhone(USER.getPhone());
        USER_DTO.setRole(USER.getRole());
        USER_DTO.setImage(USER.getImage());
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER));
        lenient().when(passwordEncoder.encode(USER.getPassword())).thenReturn(USER.getPassword());
        lenient().when(userMapper.userToUserDto(USER)).thenReturn(USER_DTO);
        lenient().when(userMapper.updateUserDtoToUser(UPDATE_USER_DTO)).thenReturn(USER);
        lenient().when(userMapper.userToUpdateUserDto(USER)).thenReturn(UPDATE_USER_DTO);
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void setPassword() {
        usersService.setPassword(NEW_PASSWORD_USER_DTO);
        verify(userRepository, times(1)).save(USER);
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void getUser() {
        assertEquals(USER_DTO, usersService.getUser());
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void updateUser() {
        assertEquals(UPDATE_USER_DTO, usersService.updateUser(UPDATE_USER_DTO));
    }
}