package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.homework.config.ClockConfig;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static ru.skypro.homework.constants.Constants.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class AdsServiceImplTest {
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
    @Mock
    AdRepository adRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    AdMapper adMapper;
    @Mock
    CommentMapper commentMapper;
    @Mock
    ClockConfig clockConfig;
    @Mock
    Clock clock;
    @InjectMocks
    AdsServiceImpl adsService;

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
        ADS_ADMIN_DTO.setCount(1);
        ADS_ADMIN_DTO.setResults(List.of(AD_3_DTO));
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
        lenient().when(clockConfig.clock()).thenReturn(clock);
        lenient().when(clock.millis()).thenReturn(111111L);
        lenient().when(userRepository.findByEmail(USER.getEmail())).thenReturn(Optional.of(USER));
        lenient().when(userRepository.findByEmail(ADMIN.getEmail())).thenReturn(Optional.of(ADMIN));
        lenient().when(adRepository.findAll()).thenReturn(ADS);
        lenient().when(adMapper.adsListToAdsDto(ADS)).thenReturn(ADS_DTO);
        lenient().when(adRepository.findByPk(1)).thenReturn(Optional.of(AD_1));
        lenient().when(adRepository.findByPk(2)).thenReturn(Optional.of(AD_2));
        lenient().when(adRepository.findByPk(3)).thenReturn(Optional.of(AD_3));
        lenient().when(adMapper.adToExtendedAd(AD_1, AD_1.getAuthor())).thenReturn(EXTENDED_AD_1_DTO);
        lenient().when(adMapper.adToExtendedAd(AD_2, AD_2.getAuthor())).thenReturn(EXTENDED_AD_2_DTO);
        lenient().when(adMapper.adToExtendedAd(AD_3, AD_3.getAuthor())).thenReturn(EXTENDED_AD_3_DTO);
        lenient().doNothing().when(adRepository).delete(any(Ad.class));
        lenient().when(adMapper.createOrUpdateAdDtoToAd(CREATE_OR_UPDATE_AD_1_DTO)).thenReturn(AD_1);
        lenient().when(adMapper.createOrUpdateAdDtoToAd(CREATE_OR_UPDATE_AD_2_DTO)).thenReturn(AD_2);
        lenient().when(adMapper.createOrUpdateAdDtoToAd(CREATE_OR_UPDATE_AD_3_DTO)).thenReturn(AD_3);
        lenient().when(adRepository.save(AD_1)).thenReturn(AD_1);
        lenient().when(adRepository.save(AD_2)).thenReturn(AD_2);
        lenient().when(adRepository.save(AD_3)).thenReturn(AD_3);
        lenient().when(adMapper.adToAdDto(AD_1)).thenReturn(AD_1_DTO);
        lenient().when(adMapper.adToAdDto(AD_2)).thenReturn(AD_2_DTO);
        lenient().when(adMapper.adToAdDto(AD_3)).thenReturn(AD_3_DTO);
        lenient().when(adRepository.findByAuthor(USER)).thenReturn(ADS_USER);
        lenient().when(adRepository.findByAuthor(ADMIN)).thenReturn(ADS_ADMIN);
        lenient().when(adMapper.adsListToAdsDto(ADS_USER)).thenReturn(ADS_USER_DTO);
        lenient().when(adMapper.adsListToAdsDto(ADS_ADMIN)).thenReturn(ADS_ADMIN_DTO);
        lenient().when(commentRepository.findByAd(AD_1)).thenReturn(COMMENTS);
        lenient().when(commentMapper.commentListToCommentsDto(COMMENTS)).thenReturn(COMMENTS_DTO);
        lenient().when(commentRepository.save(COMMENT_1_SAVE)).thenReturn(COMMENT_1);
        lenient().when(commentRepository.save(COMMENT_2_SAVE)).thenReturn(COMMENT_2);
        lenient().when(commentRepository.save(COMMENT_3_SAVE)).thenReturn(COMMENT_3);
        lenient().when(commentRepository.save(COMMENT_1)).thenReturn(COMMENT_1);
        lenient().when(commentRepository.save(COMMENT_2)).thenReturn(COMMENT_2);
        lenient().when(commentRepository.save(COMMENT_3)).thenReturn(COMMENT_3);
        lenient().when(commentMapper.commentToCommentDto(COMMENT_1)).thenReturn(COMMENT_1_DTO);
        lenient().when(commentMapper.commentToCommentDto(COMMENT_2)).thenReturn(COMMENT_2_DTO);
        lenient().when(commentMapper.commentToCommentDto(COMMENT_3)).thenReturn(COMMENT_3_DTO);
        lenient().when(commentRepository.findByPk(1)).thenReturn(Optional.of(COMMENT_1));
        lenient().when(commentRepository.findByPk(2)).thenReturn(Optional.of(COMMENT_2));
        lenient().when(commentRepository.findByPk(3)).thenReturn(Optional.of(COMMENT_3));
        lenient().doNothing().when(commentRepository).delete(any(Comment.class));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void getAll() {
        assertEquals(ADS_DTO, adsService.getAll());
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void addAd() {
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void getAd() {
        assertEquals(EXTENDED_AD_1_DTO, adsService.getAd(1));
        assertEquals(EXTENDED_AD_2_DTO, adsService.getAd(2));
        assertEquals(EXTENDED_AD_3_DTO, adsService.getAd(3));
        assertNull(adsService.getAd(4));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void deleteAdByUser() {
        assertTrue(adsService.deleteAd(1));
        assertTrue(adsService.deleteAd(2));
        assertThrows(UsernameNotFoundException.class, () -> adsService.deleteAd(3));
        assertFalse(adsService.deleteAd(4));
    }

    @Test
    @WithMockUser(value = "admin@test.com")
    void deleteAdByAdmin() {
        assertTrue(adsService.deleteAd(1));
        assertTrue(adsService.deleteAd(2));
        assertTrue(adsService.deleteAd(3));
        assertFalse(adsService.deleteAd(4));
    }

    @Test
    @WithMockUser(value = "anotheruser@test.com")
    void deleteAdByAnotherUser() {
        assertFalse(adsService.deleteAd(1));
        assertFalse(adsService.deleteAd(2));
        assertFalse(adsService.deleteAd(3));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void updateAdWithUser() {
        assertEquals(AD_1_DTO, adsService.updateAd(1, CREATE_OR_UPDATE_AD_1_DTO));
        assertEquals(AD_2_DTO, adsService.updateAd(2, CREATE_OR_UPDATE_AD_2_DTO));
        assertThrows(UsernameNotFoundException.class, () -> adsService.updateAd(3, CREATE_OR_UPDATE_AD_3_DTO));
    }

    @Test
    @WithMockUser(value = "admin@test.com")
    void updateAdWithAdmin() {
        assertEquals(AD_1_DTO, adsService.updateAd(1, CREATE_OR_UPDATE_AD_1_DTO));
        assertEquals(AD_2_DTO, adsService.updateAd(2, CREATE_OR_UPDATE_AD_2_DTO));
        assertEquals(AD_3_DTO, adsService.updateAd(3, CREATE_OR_UPDATE_AD_3_DTO));
    }

    @Test
    @WithMockUser(value = "anotheruser@test.com")
    void updateAdWithAnotherUser() {
        assertNull(adsService.updateAd(1, CREATE_OR_UPDATE_AD_1_DTO));
        assertNull(adsService.updateAd(2, CREATE_OR_UPDATE_AD_2_DTO));
        assertNull(adsService.updateAd(3, CREATE_OR_UPDATE_AD_3_DTO));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void getAllMineWithUser() {
        assertEquals(ADS_USER_DTO, adsService.getAllMine());
    }

    @Test
    @WithMockUser(value = "admin@test.com")
    void getAllMineWithAdmin() {
        assertEquals(ADS_ADMIN_DTO, adsService.getAllMine());
    }

    @Test
    @WithMockUser(value = "anotheruser@test.com")
    void getAllMineWithAnotherUser() {
        assertNull(adsService.getAllMine());
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void getAdComments() {
        assertEquals(COMMENTS_DTO, adsService.getAdComments(1));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void addComment() {
        assertEquals(COMMENT_1_DTO, adsService.addComment(1, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertEquals(COMMENT_2_DTO, adsService.addComment(1, CREATE_OR_UPDATE_COMMENT_2_DTO));
        assertNull(adsService.addComment(1, CREATE_OR_UPDATE_COMMENT_3_DTO));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void deleteCommentWithUser() {
        assertTrue(adsService.deleteComment(1, 1));
        assertTrue(adsService.deleteComment(1, 2));
        assertThrows(UsernameNotFoundException.class, () -> adsService.deleteComment(1, 3));
        assertFalse(adsService.deleteComment(1, 4));
        assertFalse(adsService.deleteComment(2, 1));
    }

    @Test
    @WithMockUser(value = "admin@test.com")
    void deleteCommentWithAdmin() {
        assertTrue(adsService.deleteComment(1, 1));
        assertTrue(adsService.deleteComment(1, 2));
        assertTrue(adsService.deleteComment(1, 3));
        assertFalse(adsService.deleteComment(1, 4));
        assertFalse(adsService.deleteComment(2, 1));
    }

    @Test
    @WithMockUser(value = "anotheruser@test.com")
    void deleteCommentWithAnotherUser() {
        assertFalse(adsService.deleteComment(1, 1));
        assertFalse(adsService.deleteComment(1, 2));
        assertFalse(adsService.deleteComment(1, 3));
        assertFalse(adsService.deleteComment(1, 4));
        assertFalse(adsService.deleteComment(2, 1));
    }

    @Test
    @WithMockUser(value = "user@test.com")
    void updateCommentWithUser() {
        assertEquals(COMMENT_1_DTO, adsService.updateComment(1, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertEquals(COMMENT_2_DTO, adsService.updateComment(1, 2, CREATE_OR_UPDATE_COMMENT_2_DTO));
        assertThrows(UsernameNotFoundException.class, () -> adsService.updateComment(1, 3, CREATE_OR_UPDATE_COMMENT_3_DTO));
        assertNull(adsService.updateComment(1, 4, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertNull(adsService.updateComment(2, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
    }

    @Test
    @WithMockUser(value = "admin@test.com")
    void updateCommentWithAdmin() {
        assertEquals(COMMENT_1_DTO, adsService.updateComment(1, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertEquals(COMMENT_2_DTO, adsService.updateComment(1, 2, CREATE_OR_UPDATE_COMMENT_2_DTO));
        assertEquals(COMMENT_3_DTO, adsService.updateComment(1, 3, CREATE_OR_UPDATE_COMMENT_3_DTO));
        assertNull(adsService.updateComment(1, 4, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertNull(adsService.updateComment(2, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
    }

    @Test
    @WithMockUser(value = "anotheruser@test.com")
    void updateCommentWithAnotherUser() {
        assertNull(adsService.updateComment(1, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertNull(adsService.updateComment(1, 2, CREATE_OR_UPDATE_COMMENT_2_DTO));
        assertNull(adsService.updateComment(1, 3, CREATE_OR_UPDATE_COMMENT_3_DTO));
        assertNull(adsService.updateComment(1, 4, CREATE_OR_UPDATE_COMMENT_1_DTO));
        assertNull(adsService.updateComment(2, 1, CREATE_OR_UPDATE_COMMENT_1_DTO));
    }
}