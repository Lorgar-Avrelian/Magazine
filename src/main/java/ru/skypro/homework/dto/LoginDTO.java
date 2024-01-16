package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Login { <br><br>
 * password	string <br>
 * minLength: 8 <br>
 * maxLength: 16 <br>
 * пароль <br><br>
 * username	string <br>
 * minLength: 4 <br>
 * maxLength: 32 <br>
 * логин <br>
 * }
 */
public class LoginDTO {
    @Schema(example = "user@test.com", description = "логин", minLength = 4, maxLength = 32)
    private String username;
    @Schema(example = "12345678", description = "пароль", minLength = 8, maxLength = 16)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
