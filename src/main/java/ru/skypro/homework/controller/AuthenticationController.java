package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.SecurityFilterChainConfig;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.AuthenticationService;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;

import javax.servlet.http.HttpServletResponse;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Previous steps of Spring Security configuration are in the {@link AuthenticationServiceImpl}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Предыдущие шаги настройки Spring Security находятся в классе {@link AuthenticationServiceImpl}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security authentication controller. <br>
 * <br>
 * Creation of this controller is the <p><b>last step</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Контроллер аутентификации Spring Security. <br>
 * <br>
 * Создание этого класса - <p><b>последний шаг</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see AuthenticationService
 * @see AuthenticationServiceImpl
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * A method of the controller for authentication users by login and password, that are contained in {@link LoginDTO}. <br>
     * <br>
     * Used service method {@link AuthenticationService#login(LoginDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для аутентификации пользователей по логину и паролю, которые содержатся в {@link LoginDTO}. <br>
     * <br>
     * Использован метод сервиса {@link AuthenticationService#login(LoginDTO)}. <br>
     * <br>
     *
     * @param loginDTO
     * @return {@link HttpServletResponse#addHeader(String, String)} or {@link HttpStatus#UNAUTHORIZED}
     * @see SecurityFilterChainConfig
     * @see AuthenticationService
     */
    @Operation(
            tags = "Авторизация",
            summary = "Авторизация пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = LoginDTO.class
                            )
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Void.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Void.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = authenticationService.login(loginDTO);
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(user.getEmail(), user.getPassword());
            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * A method of the controller for registration users with username, password, firstname, lastname, phone and {@link Role}, that are contained in {@link RegisterDTO}. <br>
     * <br>
     * Used service method {@link AuthenticationService#register(RegisterDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для регистрации пользователей по имени пользователя, паролю, имени, фамилии, телефону и {@link Role}, которые содержатся в {@link RegisterDTO}. <br>
     * <br>
     * Использован метод сервиса {@link AuthenticationService#register(RegisterDTO)}. <br>
     * <br>
     *
     * @param registerDTO
     * @return {@link HttpStatus#CREATED} and {@link HttpServletResponse#addHeader(String, String)} or {@link HttpStatus#BAD_REQUEST}
     * @see SecurityFilterChainConfig
     * @see AuthenticationService
     */
    @Operation(
            tags = "Регистрация",
            summary = "Регистрация пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = RegisterDTO.class
                            )
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Void.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Void.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        User user = authenticationService.register(registerDTO);
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(user.getEmail(), user.getPassword());
            return ResponseEntity.status(201).headers(headers).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}