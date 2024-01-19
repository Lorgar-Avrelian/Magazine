package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static ru.skypro.homework.constants.Constants.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthenticationServiceImplTest {
    private LoginDTO LOGIN_USER_DTO = new LoginDTO();
    private LoginDTO LOGIN_ANOTHER_USER_DTO = new LoginDTO();
    private LoginDTO LOGIN_ADMIN_DTO = new LoginDTO();
    private RegisterDTO REGISTER_USER_DTO = new RegisterDTO();
    private RegisterDTO REGISTER_ANOTHER_USER_DTO = new RegisterDTO();
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoderConfig encoderConfiguration;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        LOGIN_USER_DTO.setUsername(USER.getEmail());
        LOGIN_USER_DTO.setPassword(USER.getPassword());
        LOGIN_ADMIN_DTO.setUsername(ADMIN.getEmail());
        LOGIN_ADMIN_DTO.setPassword(ADMIN.getPassword());
        LOGIN_ANOTHER_USER_DTO.setUsername(ANOTHER_USER.getEmail());
        LOGIN_ANOTHER_USER_DTO.setPassword(ANOTHER_USER.getPassword());
        REGISTER_USER_DTO.setUsername(USER.getEmail());
        REGISTER_USER_DTO.setPassword(USER.getPassword());
        REGISTER_USER_DTO.setFirstName(USER.getFirstName());
        REGISTER_USER_DTO.setLastName(USER.getLastName());
        REGISTER_USER_DTO.setPhone(USER.getPhone());
        REGISTER_USER_DTO.setRole(USER.getRole());
        REGISTER_ANOTHER_USER_DTO.setUsername(ANOTHER_USER.getEmail());
        REGISTER_ANOTHER_USER_DTO.setPassword(ANOTHER_USER.getPassword());
        REGISTER_ANOTHER_USER_DTO.setFirstName(ANOTHER_USER.getFirstName());
        REGISTER_ANOTHER_USER_DTO.setLastName(ANOTHER_USER.getLastName());
        REGISTER_ANOTHER_USER_DTO.setPhone(ANOTHER_USER.getPhone());
        REGISTER_ANOTHER_USER_DTO.setRole(ANOTHER_USER.getRole());
        lenient().when(userMapper.registerDtoToUser(REGISTER_USER_DTO)).thenReturn(USER);
        lenient().when(userMapper.registerDtoToUser(REGISTER_ANOTHER_USER_DTO)).thenReturn(ANOTHER_USER);
        lenient().when(encoderConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
        lenient().when(passwordEncoder.encode(USER.getPassword())).thenReturn(USER.getPassword());
        lenient().when(passwordEncoder.encode(ADMIN.getPassword())).thenReturn(ADMIN.getPassword());
        lenient().when(passwordEncoder.encode(ANOTHER_USER.getPassword())).thenReturn(ANOTHER_USER.getPassword());
        lenient().when(userRepository.save(USER)).thenReturn(USER);
        lenient().when(userRepository.save(ADMIN)).thenReturn(ADMIN);
        lenient().when(userRepository.save(ANOTHER_USER)).thenReturn(ANOTHER_USER);
        lenient().when(userMapper.loginDtoToUser(LOGIN_USER_DTO)).thenReturn(USER);
        lenient().when(userMapper.loginDtoToUser(LOGIN_ADMIN_DTO)).thenReturn(ADMIN);
        lenient().when(userMapper.loginDtoToUser(LOGIN_ANOTHER_USER_DTO)).thenReturn(ANOTHER_USER);
        lenient().when(passwordEncoder.matches(LOGIN_USER_DTO.getPassword(), USER.getPassword())).thenReturn(true);
        lenient().when(passwordEncoder.matches(LOGIN_ADMIN_DTO.getPassword(), ADMIN.getPassword())).thenReturn(true);
        lenient().when(userRepository.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));
        lenient().when(userRepository.findByEmail(ADMIN.getEmail())).thenReturn(Optional.of(ADMIN));
    }

    @Test
    void loadUserByUsername() {
        assertNotNull(authenticationService.loadUserByUsername(USER.getEmail()));
        assertNotNull(authenticationService.loadUserByUsername(ADMIN.getEmail()));
        assertNull(authenticationService.loadUserByUsername(ANOTHER_USER.getEmail()));
    }

    @Test
    void login() {
        assertEquals(USER, authenticationService.login(LOGIN_USER_DTO));
        assertEquals(ADMIN, authenticationService.login(LOGIN_ADMIN_DTO));
        assertNull(authenticationService.login(LOGIN_ANOTHER_USER_DTO));
    }

    @Test
    void register() {
        assertNull(authenticationService.register(REGISTER_USER_DTO));
        assertEquals(ANOTHER_USER, authenticationService.register(REGISTER_ANOTHER_USER_DTO));
    }
}