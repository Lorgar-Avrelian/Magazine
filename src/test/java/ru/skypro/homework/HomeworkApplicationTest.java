package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.constants.Constants.*;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class HomeworkApplicationTest {
    private AdDTO AD_1_DTO = new AdDTO();
    private AdDTO AD_2_DTO = new AdDTO();
    private AdDTO AD_3_DTO = new AdDTO();
    private AdsDTO ADS_DTO = new AdsDTO();
    private AdsDTO ADS_USER_DTO = new AdsDTO();
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
    private NewPasswordDTO NEW_PASSWORD_USER_DTO = new NewPasswordDTO();
    private RegisterDTO REGISTER_USER_DTO = new RegisterDTO();
    private UpdateUserDTO UPDATE_USER_DTO = new UpdateUserDTO();
    private UserDTO USER_DTO = new UserDTO();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PasswordEncoderConfig passwordEncoderConfig;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    SecurityFilterChainConfig securityFilterChainConfig;
    @MockBean
    AdRepository adRepository;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AdMapper adMapper;
    @MockBean
    CommentMapper commentMapper;
    @MockBean
    UserMapper userMapper;
    @SpyBean
    AdsServiceImpl adsService;
    @SpyBean
    AuthenticationServiceImpl authenticationService;
    @SpyBean
    ImageServiceImpl imageService;
    @SpyBean
    UsersServiceImpl usersService;
    @InjectMocks
    AdsController adsController;
    @InjectMocks
    AuthenticationController authenticationController;
    @InjectMocks
    ImageController imageController;
    @InjectMocks
    UsersController usersController;

    @BeforeEach
    void setUp() {
        AD_1_DTO.setAuthor(AD_1.getAuthor().getId());
        AD_1_DTO.setImage(AD_1.getImage());
        AD_1_DTO.setPk(AD_1.getPk());
        AD_1_DTO.setPrice(AD_1.getPrice());
        AD_1_DTO.setTitle(AD_1.getTitle());
        AD_2_DTO.setAuthor(AD_2.getAuthor().getId());
        AD_2_DTO.setImage(AD_2.getImage());
        AD_2_DTO.setPk(AD_2.getPk());
        AD_2_DTO.setPrice(AD_2.getPrice());
        AD_2_DTO.setTitle(AD_2.getTitle());
        AD_3_DTO.setAuthor(AD_3.getAuthor().getId());
        AD_3_DTO.setImage(AD_3.getImage());
        AD_3_DTO.setPk(AD_3.getPk());
        AD_3_DTO.setPrice(AD_3.getPrice());
        AD_3_DTO.setTitle(AD_3.getTitle());
        ADS_DTO.setCount(3);
        ADS_DTO.setResults(List.of(AD_1_DTO, AD_2_DTO, AD_3_DTO));
        ADS_USER_DTO.setCount(2);
        ADS_USER_DTO.setResults(List.of(AD_1_DTO, AD_2_DTO));
        COMMENT_1_DTO.setAuthor(COMMENT_1.getAuthor().getId());
        COMMENT_1_DTO.setAuthorImage(COMMENT_1.getAuthorImage());
        COMMENT_1_DTO.setAuthorFirstName(COMMENT_1.getAuthorFirstName());
        COMMENT_1_DTO.setCreatedAt(COMMENT_1.getCreatedAt());
        COMMENT_1_DTO.setPk(COMMENT_1.getPk());
        COMMENT_1_DTO.setText(COMMENT_1.getText());
        COMMENT_2_DTO.setAuthor(COMMENT_2.getAuthor().getId());
        COMMENT_2_DTO.setAuthorImage(COMMENT_2.getAuthorImage());
        COMMENT_2_DTO.setAuthorFirstName(COMMENT_2.getAuthorFirstName());
        COMMENT_2_DTO.setCreatedAt(COMMENT_2.getCreatedAt());
        COMMENT_2_DTO.setPk(COMMENT_2.getPk());
        COMMENT_2_DTO.setText(COMMENT_2.getText());
        COMMENT_3_DTO.setAuthor(COMMENT_3.getAuthor().getId());
        COMMENT_3_DTO.setAuthorImage(COMMENT_3.getAuthorImage());
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
        EXTENDED_AD_1_DTO.setImage(AD_1.getImage());
        EXTENDED_AD_1_DTO.setPhone(AD_1.getAuthor().getPhone());
        EXTENDED_AD_1_DTO.setPrice(AD_1.getPrice());
        EXTENDED_AD_1_DTO.setTitle(AD_1.getTitle());
        EXTENDED_AD_2_DTO.setPk(AD_2.getPk());
        EXTENDED_AD_2_DTO.setAuthorFirstName(AD_2.getAuthor().getFirstName());
        EXTENDED_AD_2_DTO.setAuthorLastName(AD_2.getAuthor().getLastName());
        EXTENDED_AD_2_DTO.setDescription(AD_2.getDescription());
        EXTENDED_AD_2_DTO.setEmail(AD_2.getAuthor().getEmail());
        EXTENDED_AD_2_DTO.setImage(AD_2.getImage());
        EXTENDED_AD_2_DTO.setPhone(AD_2.getAuthor().getPhone());
        EXTENDED_AD_2_DTO.setPrice(AD_2.getPrice());
        EXTENDED_AD_2_DTO.setTitle(AD_2.getTitle());
        EXTENDED_AD_3_DTO.setPk(AD_3.getPk());
        EXTENDED_AD_3_DTO.setAuthorFirstName(AD_3.getAuthor().getFirstName());
        EXTENDED_AD_3_DTO.setAuthorLastName(AD_3.getAuthor().getLastName());
        EXTENDED_AD_3_DTO.setDescription(AD_3.getDescription());
        EXTENDED_AD_3_DTO.setEmail(AD_3.getAuthor().getEmail());
        EXTENDED_AD_3_DTO.setImage(AD_3.getImage());
        EXTENDED_AD_3_DTO.setPhone(AD_3.getAuthor().getPhone());
        EXTENDED_AD_3_DTO.setPrice(AD_3.getPrice());
        EXTENDED_AD_3_DTO.setTitle(AD_3.getTitle());
        LOGIN_USER_DTO.setUsername(USER.getEmail());
        LOGIN_USER_DTO.setPassword(USER.getPassword());
        LOGIN_ADMIN_DTO.setUsername(ADMIN.getEmail());
        LOGIN_ADMIN_DTO.setPassword(ADMIN.getPassword());
        NEW_PASSWORD_USER_DTO.setCurrentPassword(USER.getPassword());
        NEW_PASSWORD_USER_DTO.setNewPassword(USER.getPassword());
        REGISTER_USER_DTO.setUsername(USER.getEmail());
        REGISTER_USER_DTO.setPassword(USER.getPassword());
        REGISTER_USER_DTO.setFirstName(USER.getFirstName());
        REGISTER_USER_DTO.setLastName(USER.getLastName());
        REGISTER_USER_DTO.setPhone(USER.getPhone());
        REGISTER_USER_DTO.setRole(USER.getRole());
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
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER)).thenReturn(Optional.of(ADMIN));
        lenient().when(userMapper.userToUserDto(USER)).thenReturn(USER_DTO);
        lenient().when(userMapper.updateUserDtoToUser(any(UpdateUserDTO.class))).thenReturn(USER);
        lenient().when(userRepository.save(USER)).thenReturn(USER);
        lenient().when(userMapper.userToUpdateUserDto(USER)).thenReturn(UPDATE_USER_DTO);
        lenient().when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        lenient().when(passwordEncoder.encode(any())).thenReturn(USER.getPassword());
        lenient().when(adRepository.findAll()).thenReturn(ADS);
        lenient().when(adMapper.adsListToAdsDto(ADS)).thenReturn(ADS_DTO);
        lenient().when(adRepository.findByPk(anyInt())).thenReturn(Optional.of(AD_1));
        lenient().when(adMapper.adToExtendedAd(AD_1, USER)).thenReturn(EXTENDED_AD_1_DTO);
        lenient().doNothing().when(adRepository).delete(any(Ad.class));
        lenient().when(adMapper.createOrUpdateAdDtoToAd(any())).thenReturn(AD_1);
        lenient().when(adRepository.save(AD_1)).thenReturn(AD_1);
        lenient().when(adMapper.adToAdDto(AD_1)).thenReturn(AD_1_DTO);
        lenient().when(adRepository.findByAuthor(USER)).thenReturn(ADS_USER);
        lenient().when(adMapper.adsListToAdsDto(ADS_USER)).thenReturn(ADS_USER_DTO);
        lenient().when(commentRepository.findByAd(any(Ad.class))).thenReturn(COMMENTS);
        lenient().when(commentMapper.commentListToCommentsDto(COMMENTS)).thenReturn(COMMENTS_DTO);
        lenient().when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_1);
        lenient().when(commentMapper.commentToCommentDto(COMMENT_1)).thenReturn(COMMENT_1_DTO);
        lenient().when(commentRepository.findByPk(anyInt())).thenReturn(Optional.of(COMMENT_1));
        lenient().doNothing().when(commentRepository).delete(any(Comment.class));
        lenient().when(userMapper.registerDtoToUser(REGISTER_USER_DTO)).thenReturn(USER);
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void setPassword() throws Exception {
        mockMvc.perform(post("/users/set_password"))
               .andExpect(status().isForbidden());
        mockMvc.perform(post("/users/set_password")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(NEW_PASSWORD_USER_DTO)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void me() throws Exception {
        mockMvc.perform(get("/users/me")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(USER_DTO.getId()))
               .andExpect(jsonPath("$.email").value(USER_DTO.getEmail()))
               .andExpect(jsonPath("$.firstName").value(USER_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(USER_DTO.getLastName()))
               .andExpect(jsonPath("$.role").value(String.valueOf(USER_DTO.getRole())))
               .andExpect(jsonPath("$.image").value(USER_DTO.getImage()));
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void meUpdate() throws Exception {
        mockMvc.perform(patch("/users/me")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(UPDATE_USER_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value(UPDATE_USER_DTO.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(UPDATE_USER_DTO.getLastName()))
               .andExpect(jsonPath("$.phone").value(UPDATE_USER_DTO.getPhone()));
    }

    @Test
    void meImage() throws Exception {
    }

    @Test
    @WithMockUser(value = "user@test.com", username = "user@test.com")
    void login() throws Exception {
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void register() throws Exception {
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void getAll() throws Exception {
        mockMvc.perform(get("/ads")
                                .with(csrf())
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

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void postAd() throws Exception {
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void getAd() throws Exception {
        mockMvc.perform(get("/ads/1")
                                .with(csrf())
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
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void deleteAd() throws Exception {
        mockMvc.perform(delete("/ads/1")
                                .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void updateAd() throws Exception {
        mockMvc.perform(patch("/ads/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(CREATE_OR_UPDATE_AD_1_DTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.author").value(AD_1_DTO.getAuthor()))
               .andExpect(jsonPath("$.image").value(AD_1_DTO.getImage()))
               .andExpect(jsonPath("$.pk").value(AD_1_DTO.getPk()))
               .andExpect(jsonPath("$.price").value(AD_1_DTO.getPrice()))
               .andExpect(jsonPath("$.title").value(AD_1_DTO.getTitle()));
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void getMe() throws Exception {
        mockMvc.perform(get("/ads/me")
                                .with(csrf())
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
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void patchAdImage() throws Exception {
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void getAdComments() throws Exception {
        mockMvc.perform(get("/ads/1/comments")
                                .with(csrf())
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
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void postAdComment() throws Exception {
        mockMvc.perform(post("/ads/1/comments")
                                .with(csrf())
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
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void deleteAdComment() throws Exception {
        mockMvc.perform(delete("/ads/1/comments/1")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void updateAdComment() throws Exception {
        mockMvc.perform(patch("/ads/1/comments/1")
                                .with(csrf())
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
    }

    @Test
    @WithMockUser(value = "spring", username = "user@test.com")
    void downloadImage() throws Exception {
    }
}