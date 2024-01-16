package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.UsersService;
import ru.skypro.homework.service.impl.UsersServiceImpl;

/**
 * A controller for requests info about users. <br>
 * <br>
 * <hr>
 * <br>
 * Контроллер для запросов информации о пользователях. <br>
 * <br>
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(path = "/users")
public class UsersController {
    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    /**
     * A method of the controller for changing password of the user by sending DTO {@link NewPasswordDTO}. <br>
     * Used service method {@link UsersService#setPassword(NewPasswordDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для изменения пароля пользователя путём отправки DTO {@link NewPasswordDTO}. <br>
     * Использован метод сервиса {@link UsersService#setPassword(NewPasswordDTO)}. <br>
     * <br>
     *
     * @param newPassword
     * @return {@link HttpStatus#OK}
     * @see UsersService#setPassword(NewPasswordDTO)
     */
    @Operation(
            tags = "Пользователи",
            summary = "Обновление пароля",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = NewPasswordDTO.class
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
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Void.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(path = "/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPassword) {
        usersService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * A method of the controller for getting info about user in form of DTO {@link UserDTO}. <br>
     * Used service method {@link UsersService#getUser()}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для получения информации о пользователе в форме DTO {@link UserDTO}. <br>
     * Использован метод сервиса {@link UsersService#getUser()}. <br>
     * <br>
     *
     * @return {@link HttpStatus#OK} and {@link UserDTO}
     * @see UsersService#getUser()
     */
    @Operation(
            tags = "Пользователи",
            summary = "Получение информации об авторизованном пользователе",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(
                                    implementation = Void.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = UserDTO.class
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
    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> me() {
        return ResponseEntity.ok().body(usersService.getUser());
    }

    /**
     * A method of the controller for updating info about user by sending DTO {@link UpdateUserDTO}. <br>
     * Used service method {@link UsersService#updateUser(UpdateUserDTO)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для обновления информации о пользователе путём отправки DTO {@link UpdateUserDTO}. <br>
     * Использован метод сервиса {@link UsersService#updateUser(UpdateUserDTO)}. <br>
     * <br>
     *
     * @param updateUser
     * @return {@link HttpStatus#OK} and {@link UpdateUserDTO}
     * @see UsersService#updateUser(UpdateUserDTO)
     */
    @Operation(
            tags = "Пользователи",
            summary = "Обновление информации об авторизованном пользователе",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UpdateUserDTO.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = UpdateUserDTO.class
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
    @PatchMapping(path = "/me")
    public ResponseEntity<UpdateUserDTO> meUpdate(@RequestBody UpdateUserDTO updateUser) {
        return ResponseEntity.ok().body(usersService.updateUser(updateUser));
    }

    /**
     * A method of the controller for updating user image by sending {@link MultipartFile}. <br>
     * Used service method {@link UsersService#setImage(MultipartFile)}. <br>
     * <br>
     * <hr>
     * <br>
     * Метод контроллера для обновления изображения пользователе путём отправки {@link MultipartFile}. <br>
     * Использован метод сервиса {@link UsersService#setImage(MultipartFile)}. <br>
     * <br>
     *
     * @param image
     * @return {@link HttpStatus#OK}
     * @see UsersService#setImage(MultipartFile)
     */
    @Operation(
            tags = "Пользователи",
            summary = "Обновление аватара авторизованного пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(
                                    implementation = MultipartFile.class
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
    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> meImage(@RequestBody MultipartFile image) {
        usersService.setImage(image);
        return ResponseEntity.ok().build();
    }
}