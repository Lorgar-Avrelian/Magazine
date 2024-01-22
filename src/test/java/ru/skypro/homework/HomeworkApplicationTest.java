package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tika.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.config.ClockConfig;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.config.SecurityFilterChainConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.controller.AuthenticationController;
import ru.skypro.homework.controller.ImageController;
import ru.skypro.homework.controller.UsersController;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UsersServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.homework.constants.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class HomeworkApplicationTest {
    @Value("${user.image.dir.path}")
    private String userImageDir;
    @Value("${ad.image.dir.path}")
    private String adsImageDir;
    @Value("${source.image.dir.path}")
    private String sourceImageDir;
    private AdDTO AD_1_DTO = new AdDTO();
    private AdDTO AD_2_DTO = new AdDTO();
    private AdDTO AD_3_DTO = new AdDTO();
    private AdsDTO ADS_DTO = new AdsDTO();
    private AdsDTO ADS_USER_DTO = new AdsDTO();
    private AdsDTO ADS_ADMIN_DTO = new AdsDTO();
    private CommentDTO COMMENT_1_DTO = new CommentDTO();
    private CommentDTO COMMENT_2_DTO = new CommentDTO();
    private CommentDTO COMMENT_3_DTO = new CommentDTO();
    private CommentsDTO COMMENTS_DTO = new CommentsDTO();
    private CreateOrUpdateAdDTO CREATE_OR_UPDATE_AD_1_DTO = new CreateOrUpdateAdDTO();
    private CreateOrUpdateAdDTO CREATE_OR_UPDATE_AD_2_DTO = new CreateOrUpdateAdDTO();
    private CreateOrUpdateAdDTO CREATE_OR_UPDATE_AD_3_DTO = new CreateOrUpdateAdDTO();
    private CreateOrUpdateCommentDTO CREATE_OR_UPDATE_COMMENT_1_DTO = new CreateOrUpdateCommentDTO();
    private CreateOrUpdateCommentDTO CREATE_OR_UPDATE_COMMENT_2_DTO = new CreateOrUpdateCommentDTO();
    private CreateOrUpdateCommentDTO CREATE_OR_UPDATE_COMMENT_3_DTO = new CreateOrUpdateCommentDTO();
    private ExtendedAdDTO EXTENDED_AD_1_DTO = new ExtendedAdDTO();
    private ExtendedAdDTO EXTENDED_AD_2_DTO = new ExtendedAdDTO();
    private ExtendedAdDTO EXTENDED_AD_3_DTO = new ExtendedAdDTO();
    private LoginDTO LOGIN_USER_DTO = new LoginDTO();
    private LoginDTO LOGIN_ADMIN_DTO = new LoginDTO();
    private LoginDTO LOGIN_ANOTHER_USER_DTO = new LoginDTO();
    private NewPasswordDTO NEW_PASSWORD_USER_DTO = new NewPasswordDTO();
    private NewPasswordDTO NEW_PASSWORD_ANOTHER_USER_DTO = new NewPasswordDTO();
    private NewPasswordDTO NEW_PASSWORD_ADMIN_DTO = new NewPasswordDTO();
    private RegisterDTO REGISTER_USER_DTO = new RegisterDTO();
    private RegisterDTO REGISTER_ADMIN_DTO = new RegisterDTO();
    private RegisterDTO REGISTER_ANOTHER_USER_DTO = new RegisterDTO();
    private UpdateUserDTO UPDATE_USER_DTO = new UpdateUserDTO();
    private UpdateUserDTO UPDATE_ADMIN_DTO = new UpdateUserDTO();
    private UserDTO USER_DTO = new UserDTO();
    private UserDTO ADMIN_DTO = new UserDTO();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @MockBean
    PasswordEncoderConfig passwordEncoderConfig;
    @MockBean
    DaoAuthenticationProvider daoAuthenticationProvider;
    @MockBean
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    SecurityFilterChainConfig securityFilterChainConfig;
    @MockBean
    AdRepository adRepository;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    UserRepository userRepository;
    @Autowired
    AdMapper adMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @SpyBean
    AdsServiceImpl adsService;
    @SpyBean
    AuthenticationServiceImpl authenticationService;
    @SpyBean
    ImageServiceImpl imageService;
    @SpyBean
    UsersServiceImpl usersService;
    @Autowired
    AdsController adsController;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    ImageController imageController;
    @Autowired
    UsersController usersController;
    @MockBean
    Clock clock;
    @MockBean
    ClockConfig clockConfig;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        AD_1_DTO.setAuthor(AD_1.getAuthor().getId());
        AD_1_DTO.setImage("/" + AD_1.getImage());
        AD_1_DTO.setPk(AD_1.getPk());
        AD_1_DTO.setPrice(AD_1.getPrice());
        AD_1_DTO.setTitle(AD_1.getTitle());
        AD_2_DTO.setAuthor(AD_2.getAuthor().getId());
        AD_2_DTO.setImage("/" + AD_2.getImage());
        AD_2_DTO.setPk(AD_2.getPk());
        AD_2_DTO.setPrice(AD_2.getPrice());
        AD_2_DTO.setTitle(AD_2.getTitle());
        AD_3_DTO.setAuthor(AD_3.getAuthor().getId());
        AD_3_DTO.setImage("/" + AD_3.getImage());
        AD_3_DTO.setPk(AD_3.getPk());
        AD_3_DTO.setPrice(AD_3.getPrice());
        AD_3_DTO.setTitle(AD_3.getTitle());
        ADS_DTO.setCount(3);
        ADS_DTO.setResults(List.of(AD_1_DTO, AD_2_DTO, AD_3_DTO));
        ADS_USER_DTO.setCount(2);
        ADS_USER_DTO.setResults(List.of(AD_1_DTO, AD_2_DTO));
        ADS_ADMIN_DTO.setCount(1);
        ADS_ADMIN_DTO.setResults(List.of(AD_3_DTO));
        COMMENT_1_DTO.setAuthor(COMMENT_1.getAuthor().getId());
        COMMENT_1_DTO.setAuthorImage("/" + COMMENT_1.getAuthorImage());
        COMMENT_1_DTO.setAuthorFirstName(COMMENT_1.getAuthorFirstName());
        COMMENT_1_DTO.setCreatedAt(COMMENT_1.getCreatedAt());
        COMMENT_1_DTO.setPk(COMMENT_1.getPk());
        COMMENT_1_DTO.setText(COMMENT_1.getText());
        COMMENT_2_DTO.setAuthor(COMMENT_2.getAuthor().getId());
        COMMENT_2_DTO.setAuthorImage("/" + COMMENT_2.getAuthorImage());
        COMMENT_2_DTO.setAuthorFirstName(COMMENT_2.getAuthorFirstName());
        COMMENT_2_DTO.setCreatedAt(COMMENT_2.getCreatedAt());
        COMMENT_2_DTO.setPk(COMMENT_2.getPk());
        COMMENT_2_DTO.setText(COMMENT_2.getText());
        COMMENT_3_DTO.setAuthor(COMMENT_3.getAuthor().getId());
        COMMENT_3_DTO.setAuthorImage("/" + COMMENT_3.getAuthorImage());
        COMMENT_3_DTO.setAuthorFirstName(COMMENT_3.getAuthorFirstName());
        COMMENT_3_DTO.setCreatedAt(COMMENT_3.getCreatedAt());
        COMMENT_3_DTO.setPk(COMMENT_3.getPk());
        COMMENT_3_DTO.setText(COMMENT_3.getText());
        COMMENTS_DTO.setCount(3);
        COMMENTS_DTO.setResults(List.of(COMMENT_1_DTO, COMMENT_2_DTO, COMMENT_3_DTO));
        CREATE_OR_UPDATE_AD_1_DTO.setTitle(AD_1.getTitle());
        CREATE_OR_UPDATE_AD_1_DTO.setPrice(AD_1.getPrice());
        CREATE_OR_UPDATE_AD_1_DTO.setDescription(AD_1.getDescription());
        CREATE_OR_UPDATE_AD_2_DTO.setTitle(AD_2.getTitle());
        CREATE_OR_UPDATE_AD_2_DTO.setPrice(AD_2.getPrice());
        CREATE_OR_UPDATE_AD_2_DTO.setDescription(AD_2.getDescription());
        CREATE_OR_UPDATE_AD_3_DTO.setTitle(AD_3.getTitle());
        CREATE_OR_UPDATE_AD_3_DTO.setPrice(AD_3.getPrice());
        CREATE_OR_UPDATE_AD_3_DTO.setDescription(AD_3.getDescription());
        CREATE_OR_UPDATE_COMMENT_1_DTO.setText(COMMENT_1.getText());
        CREATE_OR_UPDATE_COMMENT_2_DTO.setText(COMMENT_2.getText());
        CREATE_OR_UPDATE_COMMENT_3_DTO.setText(COMMENT_3.getText());
        EXTENDED_AD_1_DTO.setPk(AD_1.getPk());
        EXTENDED_AD_1_DTO.setAuthorFirstName(AD_1.getAuthor().getFirstName());
        EXTENDED_AD_1_DTO.setAuthorLastName(AD_1.getAuthor().getLastName());
        EXTENDED_AD_1_DTO.setDescription(AD_1.getDescription());
        EXTENDED_AD_1_DTO.setEmail(AD_1.getAuthor().getEmail());
        EXTENDED_AD_1_DTO.setImage("/" + AD_1.getImage());
        EXTENDED_AD_1_DTO.setPhone(AD_1.getAuthor().getPhone());
        EXTENDED_AD_1_DTO.setPrice(AD_1.getPrice());
        EXTENDED_AD_1_DTO.setTitle(AD_1.getTitle());
        EXTENDED_AD_2_DTO.setPk(AD_2.getPk());
        EXTENDED_AD_2_DTO.setAuthorFirstName(AD_2.getAuthor().getFirstName());
        EXTENDED_AD_2_DTO.setAuthorLastName(AD_2.getAuthor().getLastName());
        EXTENDED_AD_2_DTO.setDescription(AD_2.getDescription());
        EXTENDED_AD_2_DTO.setEmail(AD_2.getAuthor().getEmail());
        EXTENDED_AD_2_DTO.setImage("/" + AD_2.getImage());
        EXTENDED_AD_2_DTO.setPhone(AD_2.getAuthor().getPhone());
        EXTENDED_AD_2_DTO.setPrice(AD_2.getPrice());
        EXTENDED_AD_2_DTO.setTitle(AD_2.getTitle());
        EXTENDED_AD_3_DTO.setPk(AD_3.getPk());
        EXTENDED_AD_3_DTO.setAuthorFirstName(AD_3.getAuthor().getFirstName());
        EXTENDED_AD_3_DTO.setAuthorLastName(AD_3.getAuthor().getLastName());
        EXTENDED_AD_3_DTO.setDescription(AD_3.getDescription());
        EXTENDED_AD_3_DTO.setEmail(AD_3.getAuthor().getEmail());
        EXTENDED_AD_3_DTO.setImage("/" + AD_3.getImage());
        EXTENDED_AD_3_DTO.setPhone(AD_3.getAuthor().getPhone());
        EXTENDED_AD_3_DTO.setPrice(AD_3.getPrice());
        EXTENDED_AD_3_DTO.setTitle(AD_3.getTitle());
        LOGIN_USER_DTO.setUsername(USER.getEmail());
        LOGIN_USER_DTO.setPassword(USER.getPassword());
        LOGIN_ADMIN_DTO.setUsername(ADMIN.getEmail());
        LOGIN_ADMIN_DTO.setPassword(ADMIN.getPassword());
        LOGIN_ANOTHER_USER_DTO.setUsername(ANOTHER_USER.getEmail());
        LOGIN_ANOTHER_USER_DTO.setPassword(ANOTHER_USER.getPassword());
        NEW_PASSWORD_USER_DTO.setCurrentPassword(USER.getPassword());
        NEW_PASSWORD_USER_DTO.setNewPassword(ADMIN.getPassword());
        NEW_PASSWORD_ADMIN_DTO.setCurrentPassword(ADMIN.getPassword());
        NEW_PASSWORD_ADMIN_DTO.setNewPassword(USER.getPassword());
        NEW_PASSWORD_ANOTHER_USER_DTO.setCurrentPassword(ANOTHER_USER.getPassword());
        NEW_PASSWORD_ANOTHER_USER_DTO.setNewPassword(ADMIN.getPassword());
        REGISTER_USER_DTO.setUsername(USER.getEmail());
        REGISTER_USER_DTO.setPassword(USER.getPassword());
        REGISTER_USER_DTO.setFirstName(USER.getFirstName());
        REGISTER_USER_DTO.setLastName(USER.getLastName());
        REGISTER_USER_DTO.setPhone(USER.getPhone());
        REGISTER_USER_DTO.setRole(USER.getRole());
        REGISTER_ADMIN_DTO.setUsername(ADMIN.getEmail());
        REGISTER_ADMIN_DTO.setPassword(ADMIN.getPassword());
        REGISTER_ADMIN_DTO.setFirstName(ADMIN.getFirstName());
        REGISTER_ADMIN_DTO.setLastName(ADMIN.getLastName());
        REGISTER_ADMIN_DTO.setPhone(ADMIN.getPhone());
        REGISTER_ADMIN_DTO.setRole(ADMIN.getRole());
        REGISTER_ANOTHER_USER_DTO.setUsername(ANOTHER_USER.getEmail());
        REGISTER_ANOTHER_USER_DTO.setPassword(ANOTHER_USER.getPassword());
        REGISTER_ANOTHER_USER_DTO.setFirstName(ANOTHER_USER.getFirstName());
        REGISTER_ANOTHER_USER_DTO.setLastName(ANOTHER_USER.getLastName());
        REGISTER_ANOTHER_USER_DTO.setPhone(ANOTHER_USER.getPhone());
        REGISTER_ANOTHER_USER_DTO.setRole(ANOTHER_USER.getRole());
        UPDATE_USER_DTO.setFirstName(USER.getFirstName());
        UPDATE_USER_DTO.setLastName(USER.getLastName());
        UPDATE_USER_DTO.setPhone(USER.getPhone());
        UPDATE_ADMIN_DTO.setFirstName(ADMIN.getFirstName());
        UPDATE_ADMIN_DTO.setLastName(ADMIN.getLastName());
        UPDATE_ADMIN_DTO.setPhone(ADMIN.getPhone());
        USER_DTO.setId(USER.getId());
        USER_DTO.setEmail(USER.getEmail());
        USER_DTO.setFirstName(USER.getFirstName());
        USER_DTO.setLastName(USER.getLastName());
        USER_DTO.setPhone(USER.getPhone());
        USER_DTO.setRole(USER.getRole());
        USER_DTO.setImage("/" + USER.getImage());
        ADMIN_DTO.setId(ADMIN.getId());
        ADMIN_DTO.setEmail(ADMIN.getEmail());
        ADMIN_DTO.setFirstName(ADMIN.getFirstName());
        ADMIN_DTO.setLastName(ADMIN.getLastName());
        ADMIN_DTO.setPhone(ADMIN.getPhone());
        ADMIN_DTO.setRole(ADMIN.getRole());
        ADMIN_DTO.setImage("/" + ADMIN.getImage());
        lenient().when(clockConfig.clock()).thenReturn(clock);
        lenient().when(clock.millis()).thenReturn(111111L);
        lenient().when(userRepository.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));
        lenient().when(userRepository.findByEmail(ADMIN.getEmail())).thenReturn(Optional.of(ADMIN));
        lenient().when(userRepository.save(USER)).thenReturn(USER);
        lenient().when(userRepository.save(ADMIN)).thenReturn(ADMIN);
        lenient().when(userRepository.save(ANOTHER_USER_REGISTER)).thenReturn(ANOTHER_USER);
        lenient().when(passwordEncoderConfig.passwordEncoder()).thenReturn(passwordEncoder);
        lenient().when(passwordEncoder.encode(USER.getPassword())).thenReturn(USER.getPassword());
        lenient().when(passwordEncoder.encode(ADMIN.getPassword())).thenReturn(ADMIN.getPassword());
        lenient().when(passwordEncoder.encode(ANOTHER_USER.getPassword())).thenReturn(ANOTHER_USER.getPassword());
        lenient().when(passwordEncoder.matches(USER.getPassword(), USER.getPassword())).thenReturn(true);
        lenient().when(passwordEncoder.matches(ADMIN.getPassword(), ADMIN.getPassword())).thenReturn(true);
        lenient().when(adRepository.findAll()).thenReturn(ADS);
        lenient().when(adRepository.findByPk(AD_1.getPk())).thenReturn(Optional.of(AD_1));
        lenient().when(adRepository.findByPk(AD_2.getPk())).thenReturn(Optional.of(AD_2));
        lenient().when(adRepository.findByPk(AD_3.getPk())).thenReturn(Optional.of(AD_3));
        lenient().doNothing().when(adRepository).delete(any(Ad.class));
        lenient().when(adRepository.save(AD_1)).thenReturn(AD_1);
        lenient().when(adRepository.save(AD_2)).thenReturn(AD_2);
        lenient().when(adRepository.save(AD_3)).thenReturn(AD_3);
        lenient().when(adRepository.save(AD_1_CREATE)).thenReturn(AD_1);
        lenient().when(adRepository.save(AD_2_CREATE)).thenReturn(AD_2);
        lenient().when(adRepository.save(AD_3_CREATE)).thenReturn(AD_3);
        lenient().when(adRepository.findByAuthor(USER)).thenReturn(ADS_USER);
        lenient().when(adRepository.findByAuthor(ADMIN)).thenReturn(ADS_ADMIN);
        lenient().when(commentRepository.findByAd(AD_1)).thenReturn(COMMENTS);
        lenient().when(commentRepository.save(COMMENT_1_SAVE)).thenReturn(COMMENT_1);
        lenient().when(commentRepository.save(COMMENT_2_SAVE)).thenReturn(COMMENT_2);
        lenient().when(commentRepository.save(COMMENT_3_SAVE)).thenReturn(COMMENT_3);
        lenient().when(commentRepository.save(COMMENT_1)).thenReturn(COMMENT_1);
        lenient().when(commentRepository.save(COMMENT_2)).thenReturn(COMMENT_2);
        lenient().when(commentRepository.save(COMMENT_3)).thenReturn(COMMENT_3);
        lenient().when(commentRepository.findByPk(COMMENT_1.getPk())).thenReturn(Optional.of(COMMENT_1));
        lenient().when(commentRepository.findByPk(COMMENT_2.getPk())).thenReturn(Optional.of(COMMENT_2));
        lenient().when(commentRepository.findByPk(COMMENT_3.getPk())).thenReturn(Optional.of(COMMENT_3));
        lenient().doNothing().when(commentRepository).delete(any(Comment.class));
    }

    /**
     * Should return status code {@link HttpStatus#OK} when password changed. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when current password not equals user password. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK}, когда пароль изменён. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда текущий пароль не совпадает с паролем пользователя. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void setPassword() throws Exception {
        mockMvc.perform(post("/users/set_password")
                                .with(user(USER.getEmail()).password(USER.getPassword()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(NEW_PASSWORD_USER_DTO)))
               .andExpect(status().isOk());
        mockMvc.perform(post("/users/set_password")
                                .with(user(ADMIN.getEmail()).password(ADMIN.getPassword()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(NEW_PASSWORD_ADMIN_DTO)))
               .andExpect(status().isOk());
        mockMvc.perform(post("/users/set_password")
                                .with(anonymous())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(NEW_PASSWORD_ANOTHER_USER_DTO)))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/users/set_password")
                                .with(user(ADMIN.getEmail()).password(ADMIN.getPassword()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(NEW_PASSWORD_ANOTHER_USER_DTO)))
               .andExpect(status().isForbidden());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link UserDTO} when user is authorized. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link UserDTO}, когда пользователь авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void me() throws Exception {
        mockMvc.perform(get("/users/me")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(USER_DTO.getId()))
               .andExpect(jsonPath("$.email").value(USER_DTO.getEmail()))
               .andExpect(jsonPath("$.firstName").value(USER_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(USER_DTO.getLastName()))
               .andExpect(jsonPath("$.role").value(String.valueOf(USER_DTO.getRole())))
               .andExpect(jsonPath("$.image").value(USER_DTO.getImage()));
        mockMvc.perform(get("/users/me")
                                .with(user("admin@test.com").password("321"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(ADMIN_DTO.getId()))
               .andExpect(jsonPath("$.email").value(ADMIN_DTO.getEmail()))
               .andExpect(jsonPath("$.firstName").value(ADMIN_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(ADMIN_DTO.getLastName()))
               .andExpect(jsonPath("$.role").value(String.valueOf(ADMIN_DTO.getRole())))
               .andExpect(jsonPath("$.image").value(ADMIN_DTO.getImage()));
        mockMvc.perform(get("/users/me")
                                .with(anonymous())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link UpdateUserDTO} when user is authorized. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link UpdateUserDTO}, когда пользователь авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void meUpdate() throws Exception {
        mockMvc.perform(patch("/users/me")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(UPDATE_USER_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value(UPDATE_USER_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(UPDATE_USER_DTO.getLastName()))
               .andExpect(jsonPath("$.phone").value(UPDATE_USER_DTO.getPhone()));
        mockMvc.perform(patch("/users/me")
                                .with(user("admin@test.com").password("321"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(UPDATE_ADMIN_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value(UPDATE_ADMIN_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(UPDATE_ADMIN_DTO.getLastName()))
               .andExpect(jsonPath("$.phone").value(UPDATE_ADMIN_DTO.getPhone()));
        mockMvc.perform(patch("/users/me")
                                .with(anonymous())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(UPDATE_ADMIN_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#OK} when user is authorized. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK}, когда пользователь авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void meImage() throws Exception {
        byte[] inputImage = Files.readAllBytes(Path.of(sourceImageDir, "user.jpg"));
        MockMultipartFile multipartFile = new MockMultipartFile("image", "user.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image").file(multipartFile)
                                                                      .with(user("user@test.com").password("123")))
               .andExpect(status().isOk());
        byte[] result = Files.readAllBytes(Path.of(userImageDir, USER.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(result);
                InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, USER.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "admin.jpg"));
        multipartFile = new MockMultipartFile("image", "admin.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image").file(multipartFile)
                                                                      .with(user("admin@test.com").password("321")))
               .andExpect(status().isOk());
        result = Files.readAllBytes(Path.of(userImageDir, ADMIN.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(result);
                InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, ADMIN.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "user.jpg"));
        multipartFile = new MockMultipartFile("image", "user.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image").file(multipartFile)
                                                                      .with(anonymous()))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#OK} when user is authorized. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK}, когда пользователь авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void login() throws Exception {
        mockMvc.perform(post("/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(LOGIN_USER_DTO)))
               .andExpect(status().isOk());
        mockMvc.perform(post("/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(LOGIN_ADMIN_DTO)))
               .andExpect(status().isOk());
        mockMvc.perform(post("/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(LOGIN_ANOTHER_USER_DTO)))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#CREATED} when user is registered. <br>
     * Should return status code {@link HttpStatus#BAD_REQUEST} when user already registered before. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#CREATED}, когда пользователь зарегистрирован. <br>
     * Должен возвращать статус-код {@link HttpStatus#BAD_REQUEST}, когда пользователь был зарегистрирован ранее. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void register() throws Exception {
        mockMvc.perform(post("/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(REGISTER_USER_DTO)))
               .andExpect(status().isBadRequest());
        mockMvc.perform(post("/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(REGISTER_ADMIN_DTO)))
               .andExpect(status().isBadRequest());
        mockMvc.perform(post("/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(REGISTER_ANOTHER_USER_DTO)))
               .andExpect(status().isCreated());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link AdsDTO}. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} {@link AdsDTO}. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/ads")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.count").value(ADS_DTO.getCount()))
               .andExpect(jsonPath("$.results[0].author").value(AD_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[0].image").value(AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.results[0].pk").value(AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.results[0].price").value(AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.results[0].title").value(AD_1_DTO.getTitle()))
               .andExpect(jsonPath("$.results[1].author").value(AD_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[1].image").value(AD_2_DTO.getImage()))
               .andExpect(jsonPath("$.results[1].pk").value(AD_2_DTO.getPk()))
               .andExpect(jsonPath("$.results[1].price").value(AD_2_DTO.getPrice()))
               .andExpect(jsonPath("$.results[1].title").value(AD_2_DTO.getTitle()))
               .andExpect(jsonPath("$.results[2].author").value(AD_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[2].image").value(AD_3_DTO.getImage()))
               .andExpect(jsonPath("$.results[2].pk").value(AD_3_DTO.getPk()))
               .andExpect(jsonPath("$.results[2].price").value(AD_3_DTO.getPrice()))
               .andExpect(jsonPath("$.results[2].title").value(AD_3_DTO.getTitle()));
    }

    /**
     * Should return status code {@link HttpStatus#CREATED} and {@link AdDTO} when ad created. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#CREATED} и {@link AdDTO}, когда объявление создано. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void postAd() throws Exception {
        byte[] inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_1.jpg"));
        MockMultipartFile multipartFile = new MockMultipartFile("image", "ad_1.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        MockMultipartFile propertiesFile = new MockMultipartFile("properties", "properties", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_1_DTO).getBytes());
        mockMvc.perform(multipart(HttpMethod.POST, "/ads").file(multipartFile).file(propertiesFile)
                                                          .with(user("user@test.com").password("123"))
                                                          .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                          .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.author").value(AD_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_1_DTO.getTitle()));
        byte[] outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_1.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_2.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_2.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        propertiesFile = new MockMultipartFile("properties", "properties", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO).getBytes());
        mockMvc.perform(multipart(HttpMethod.POST, "/ads").file(multipartFile).file(propertiesFile)
                                                          .with(user("user@test.com").password("123"))
                                                          .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                          .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.author").value(AD_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_2_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_2_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_2_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_2_DTO.getTitle()));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_2.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_3.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_3.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        propertiesFile = new MockMultipartFile("properties", "properties", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_3_DTO).getBytes());
        mockMvc.perform(multipart(HttpMethod.POST, "/ads").file(multipartFile).file(propertiesFile)
                                                          .with(user("admin@test.com").password("321"))
                                                          .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                          .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.author").value(AD_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_3_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_3_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_3_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_3_DTO.getTitle()));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_3.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_1.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_1.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        propertiesFile = new MockMultipartFile("properties", "properties", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_1_DTO).getBytes());
        mockMvc.perform(multipart(HttpMethod.POST, "/ads").file(multipartFile).file(propertiesFile)
                                                          .with(anonymous())
                                                          .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                          .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link ExtendedAdDTO} when ad is exist. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link ExtendedAdDTO}, когда объявление существует. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда объявление не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void getAd() throws Exception {
        mockMvc.perform(get("/ads/1")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.pk").value(EXTENDED_AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.authorFirstName").value(EXTENDED_AD_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.authorLastName").value(EXTENDED_AD_1_DTO.getAuthorLastName()))
               .andExpect(jsonPath("$.description").value(EXTENDED_AD_1_DTO.getDescription()))
               .andExpect(jsonPath("$.email").value(EXTENDED_AD_1_DTO.getEmail()))
               .andExpect(jsonPath("$.image").value(EXTENDED_AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.phone").value(EXTENDED_AD_1_DTO.getPhone()))
               .andExpect(jsonPath("$.price").value(EXTENDED_AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(EXTENDED_AD_1_DTO.getTitle()));
        mockMvc.perform(get("/ads/1")
                                .with(user("admin@test.com").password("321"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.pk").value(EXTENDED_AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.authorFirstName").value(EXTENDED_AD_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.authorLastName").value(EXTENDED_AD_1_DTO.getAuthorLastName()))
               .andExpect(jsonPath("$.description").value(EXTENDED_AD_1_DTO.getDescription()))
               .andExpect(jsonPath("$.email").value(EXTENDED_AD_1_DTO.getEmail()))
               .andExpect(jsonPath("$.image").value(EXTENDED_AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.phone").value(EXTENDED_AD_1_DTO.getPhone()))
               .andExpect(jsonPath("$.price").value(EXTENDED_AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(EXTENDED_AD_1_DTO.getTitle()));
        mockMvc.perform(get("/ads/1")
                                .with(anonymous())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/ads/4")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#NO_CONTENT} when ad is deleted. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when user doesn't have such rights. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#NO_CONTENT}, когда объявление удалено. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда пользователь не имеет таких прав. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда объявление не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void deleteAd() throws Exception {
        mockMvc.perform(delete("/ads/1")
                                .with(user("user@test.com").password("123")))
               .andExpect(status().isNoContent());
        mockMvc.perform(delete("/ads/1")
                                .with(user("admin@test.com").password("321")))
               .andExpect(status().isNoContent());
        mockMvc.perform(delete("/ads/1")
                                .with(anonymous()))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/ads/3")
                                .with(user("user@test.com").password("123")))
               .andExpect(status().isForbidden());
        mockMvc.perform(delete("/ads/4")
                                .with(user("user@test.com").password("123")))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link AdDTO} when ad is updated. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when user doesn't have such rights. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link AdDTO}, когда объявление обновлено. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда пользователь не имеет таких прав. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда объявление не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void updateAd() throws Exception {
        mockMvc.perform(patch("/ads/2")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(AD_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_2_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_2_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_2_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_2_DTO.getTitle()));
        mockMvc.perform(patch("/ads/2")
                                .with(user("admin@test.com").password("321"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(AD_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_2_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_2_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_2_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_2_DTO.getTitle()));
        mockMvc.perform(patch("/ads/2")
                                .with(anonymous())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(patch("/ads/3")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isForbidden());
        mockMvc.perform(patch("/ads/4")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_2_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link AdsDTO} when user is authorized. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link AdsDTO}, когда пользователь авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void getMe() throws Exception {
        mockMvc.perform(get("/ads/me")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.count").value(ADS_USER_DTO.getCount()))
               .andExpect(jsonPath("$.results[0].author").value(AD_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[0].image").value(AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.results[0].pk").value(AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.results[0].price").value(AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.results[0].title").value(AD_1_DTO.getTitle()))
               .andExpect(jsonPath("$.results[1].author").value(AD_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[1].image").value(AD_2_DTO.getImage()))
               .andExpect(jsonPath("$.results[1].pk").value(AD_2_DTO.getPk()))
               .andExpect(jsonPath("$.results[1].price").value(AD_2_DTO.getPrice()))
               .andExpect(jsonPath("$.results[1].title").value(AD_2_DTO.getTitle()));
        mockMvc.perform(get("/ads/me")
                                .with(user("admin@test.com").password("321"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.count").value(ADS_ADMIN_DTO.getCount()))
               .andExpect(jsonPath("$.results[0].author").value(AD_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[0].image").value(AD_3_DTO.getImage()))
               .andExpect(jsonPath("$.results[0].pk").value(AD_3_DTO.getPk()))
               .andExpect(jsonPath("$.results[0].price").value(AD_3_DTO.getPrice()))
               .andExpect(jsonPath("$.results[0].title").value(AD_3_DTO.getTitle()));
        mockMvc.perform(get("/ads/me")
                                .with(anonymous())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link String} when ad image is updated. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when user doesn't have such rights. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link String}, когда изображение объявления обновлено. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда пользователь не имеет таких прав. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда объявление не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void patchAdImage() throws Exception {
        byte[] inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_1.jpg"));
        MockMultipartFile multipartFile = new MockMultipartFile("image", "ad_1.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/1/image").file(multipartFile)
                                                                   .with(user("user@test.com").password("123"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().string(String.valueOf(Path.of(adsImageDir, AD_1.getImage()))));
        byte[] outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_1.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_2.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_2.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/2/image").file(multipartFile)
                                                                   .with(user("user@test.com").password("123"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().string(String.valueOf(Path.of(adsImageDir, AD_2.getImage()))));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_2.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_3.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_3.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/3/image").file(multipartFile)
                                                                   .with(user("user@test.com").password("123"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isForbidden());
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_1.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_1.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/1/image").file(multipartFile)
                                                                   .with(user("admin@test.com").password("321"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().string(String.valueOf(Path.of(adsImageDir, AD_1.getImage()))));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_1.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_2.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_2.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/2/image").file(multipartFile)
                                                                   .with(user("admin@test.com").password("321"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().string(String.valueOf(Path.of(adsImageDir, AD_2.getImage()))));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_2.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_3.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_3.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/3/image").file(multipartFile)
                                                                   .with(user("admin@test.com").password("321"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().string(String.valueOf(Path.of(adsImageDir, AD_3.getImage()))));
        outputImage = Files.readAllBytes(Path.of(adsImageDir, AD_3.getImage()));
        try (
                InputStream inputStream1 = new ByteArrayInputStream(inputImage);
                InputStream inputStream2 = new ByteArrayInputStream(outputImage)
        ) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_3.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_3.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/3/image").file(multipartFile)
                                                                   .with(anonymous())
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        inputImage = Files.readAllBytes(Path.of(sourceImageDir, "ad_1.jpg"));
        multipartFile = new MockMultipartFile("image", "ad_1.jpg", MediaType.IMAGE_JPEG_VALUE, inputImage);
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/4/image").file(multipartFile)
                                                                   .with(user("user@test.com").password("123"))
                                                                   .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link CommentsDTO} when ad comments are exist. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad comments are not exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link CommentsDTO}, когда комментарии к объявлению существуют. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда комментарии к объявлению не существуют. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void getAdComments() throws Exception {
        mockMvc.perform(get("/ads/1/comments")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.count").value(COMMENTS_DTO.getCount()))
               .andExpect(jsonPath("$.results[0].author").value(COMMENT_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[0].authorImage").value(COMMENT_1_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[0].authorFirstName").value(COMMENT_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[0].createdAt").value(COMMENT_1_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[0].pk").value(COMMENT_1_DTO.getPk()))
               .andExpect(jsonPath("$.results[0].text").value(COMMENT_1_DTO.getText()))
               .andExpect(jsonPath("$.results[1].author").value(COMMENT_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[1].authorImage").value(COMMENT_2_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[1].authorFirstName").value(COMMENT_2_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[1].createdAt").value(COMMENT_2_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[1].pk").value(COMMENT_2_DTO.getPk()))
               .andExpect(jsonPath("$.results[1].text").value(COMMENT_2_DTO.getText()))
               .andExpect(jsonPath("$.results[2].author").value(COMMENT_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[2].authorImage").value(COMMENT_3_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[2].authorFirstName").value(COMMENT_3_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[2].createdAt").value(COMMENT_3_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[2].pk").value(COMMENT_3_DTO.getPk()))
               .andExpect(jsonPath("$.results[2].text").value(COMMENT_3_DTO.getText()));
        mockMvc.perform(get("/ads/1/comments")
                                .with(user("admin@test.com").password("321"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.count").value(COMMENTS_DTO.getCount()))
               .andExpect(jsonPath("$.results[0].author").value(COMMENT_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[0].authorImage").value(COMMENT_1_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[0].authorFirstName").value(COMMENT_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[0].createdAt").value(COMMENT_1_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[0].pk").value(COMMENT_1_DTO.getPk()))
               .andExpect(jsonPath("$.results[0].text").value(COMMENT_1_DTO.getText()))
               .andExpect(jsonPath("$.results[1].author").value(COMMENT_2_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[1].authorImage").value(COMMENT_2_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[1].authorFirstName").value(COMMENT_2_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[1].createdAt").value(COMMENT_2_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[1].pk").value(COMMENT_2_DTO.getPk()))
               .andExpect(jsonPath("$.results[1].text").value(COMMENT_2_DTO.getText()))
               .andExpect(jsonPath("$.results[2].author").value(COMMENT_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.results[2].authorImage").value(COMMENT_3_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.results[2].authorFirstName").value(COMMENT_3_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.results[2].createdAt").value(COMMENT_3_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.results[2].pk").value(COMMENT_3_DTO.getPk()))
               .andExpect(jsonPath("$.results[2].text").value(COMMENT_3_DTO.getText()));
        mockMvc.perform(get("/ads/1/comments")
                                .with(anonymous())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/ads/2/comments")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link CommentDTO} when ad comment is added. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link CommentDTO}, когда комментарий к объявлению добавлен. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда объявление не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void postAdComment() throws Exception {
        mockMvc.perform(post("/ads/1/comments")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(COMMENT_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.authorImage").value(COMMENT_1_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.authorFirstName").value(COMMENT_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.createdAt").value(COMMENT_1_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.pk").value(COMMENT_1_DTO.getPk()))
               .andExpect(jsonPath("$.text").value(COMMENT_1_DTO.getText()));
        mockMvc.perform(post("/ads/1/comments")
                                .with(user("admin@test.com").password("321"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_3_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(COMMENT_3_DTO.getAuthor()))
               .andExpect(jsonPath("$.authorImage").value(COMMENT_3_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.authorFirstName").value(COMMENT_3_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.createdAt").value(COMMENT_3_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.pk").value(COMMENT_3_DTO.getPk()))
               .andExpect(jsonPath("$.text").value(COMMENT_3_DTO.getText()));
        mockMvc.perform(post("/ads/3/comments")
                                .with(anonymous())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/ads/4/comments")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} when ad comment is deleted. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when user doesn't have such rights. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad comment isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK}, когда комментарий к объявлению удалён. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда пользователь не имеет таких прав. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда комментарий к объявлению не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void deleteAdComment() throws Exception {
        mockMvc.perform(delete("/ads/1/comments/1")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
        mockMvc.perform(delete("/ads/1/comments/1")
                                .with(user("admin@test.com").password("321"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
        mockMvc.perform(delete("/ads/1/comments/1")
                                .with(anonymous())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/ads/1/comments/3")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isForbidden());
        mockMvc.perform(delete("/ads/2/comments/1")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
        mockMvc.perform(delete("/ads/1/comments/4")
                                .with(user("user@test.com").password("123"))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link CommentDTO} when ad comment is updated. <br>
     * Should return status code {@link HttpStatus#UNAUTHORIZED} when user isn't authorized. <br>
     * Should return status code {@link HttpStatus#FORBIDDEN} when user doesn't have such rights. <br>
     * Should return status code {@link HttpStatus#NOT_FOUND} when ad comment isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link CommentDTO}, когда комментарий к объявлению обновлён. <br>
     * Должен возвращать статус-код {@link HttpStatus#UNAUTHORIZED}, когда пользователь не авторизован. <br>
     * Должен возвращать статус-код {@link HttpStatus#FORBIDDEN}, когда пользователь не имеет таких прав. <br>
     * Должен возвращать статус-код {@link HttpStatus#NOT_FOUND}, когда комментарий к объявлению не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void updateAdComment() throws Exception {
        mockMvc.perform(patch("/ads/1/comments/1")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(COMMENT_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.authorImage").value(COMMENT_1_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.authorFirstName").value(COMMENT_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.createdAt").value(COMMENT_1_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.pk").value(COMMENT_1_DTO.getPk()))
               .andExpect(jsonPath("$.text").value(COMMENT_1_DTO.getText()));
        mockMvc.perform(patch("/ads/1/comments/1")
                                .with(user("admin@test.com").password("321"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(COMMENT_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.authorImage").value(COMMENT_1_DTO.getAuthorImage()))
               .andExpect(jsonPath("$.authorFirstName").value(COMMENT_1_DTO.getAuthorFirstName()))
               .andExpect(jsonPath("$.createdAt").value(COMMENT_1_DTO.getCreatedAt()))
               .andExpect(jsonPath("$.pk").value(COMMENT_1_DTO.getPk()))
               .andExpect(jsonPath("$.text").value(COMMENT_1_DTO.getText()));
        mockMvc.perform(patch("/ads/1/comments/1")
                                .with(anonymous())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        mockMvc.perform(patch("/ads/1/comments/3")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_3_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isForbidden());
        mockMvc.perform(patch("/ads/2/comments/1")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
        mockMvc.perform(patch("/ads/1/comments/4")
                                .with(user("user@test.com").password("123"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_COMMENT_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }

    /**
     * Should return status code {@link HttpStatus#OK} and {@link ByteArrayOutputStream} when image is exist. <br>
     * Should return status code {@link HttpStatus#BAD_REQUEST} when image isn't exist. <br>
     * <br>
     * <hr>
     * <br>
     * Должен возвращать статус-код {@link HttpStatus#OK} и {@link ByteArrayOutputStream}, когда изображение существует. <br>
     * Должен возвращать статус-код {@link HttpStatus#BAD_REQUEST}, когда изображение не существует. <br>
     * <br>
     *
     * @throws Exception
     */
    @Test
    void downloadImage() throws Exception {
        byte[] result = mockMvc.perform(get("/" + USER.getImage())
                                                .with(user("user@test.com").password("123")))
                               .andExpect(status().isOk())
                               .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, USER.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + ADMIN.getImage())
                                         .with(user("user@test.com").password("123")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, ADMIN.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + USER.getImage())
                                         .with(user("admin@test.com").password("321")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, USER.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + ADMIN.getImage())
                                         .with(user("admin@test.com").password("321")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(userImageDir, ADMIN.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + AD_1.getImage())
                                         .with(user("user@test.com").password("123")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(adsImageDir, AD_1.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + AD_2.getImage())
                                         .with(user("admin@test.com").password("321")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(adsImageDir, AD_2.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        result = mockMvc.perform(get("/" + AD_3.getImage())
                                         .with(anonymous()))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsByteArray();
        try (InputStream inputStream1 = new ByteArrayInputStream(result);
             InputStream inputStream2 = new FileInputStream(Path.of(adsImageDir, AD_3.getImage()).toFile())) {
            assertTrue(IOUtils.contentEquals(inputStream1, inputStream2));
        }
        mockMvc.perform(get("/wrong_image_name.jpg")
                                .with(user("user@test.com").password("123")))
               .andExpect(status().isBadRequest());
        mockMvc.perform(get("/wrong_image_name.jpg")
                                .with(user("admin@test.com").password("321")))
               .andExpect(status().isBadRequest());
        mockMvc.perform(get("/wrong_image_name.jpg")
                                .with(anonymous()))
               .andExpect(status().isBadRequest());
    }
}