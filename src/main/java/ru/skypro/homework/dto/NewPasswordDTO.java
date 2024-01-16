package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO <br>
 * <hr>
 * <br>
 * NewPassword { <br><br>
 * currentPassword	string <br>
 * minLength: 8 <br>
 * maxLength: 16 <br>
 * текущий пароль <br><br>
 * newPassword	string <br>
 * minLength: 8 <br>
 * maxLength: 16 <br>
 * новый пароль <br>
 * }
 */
public class NewPasswordDTO {
    @Schema(example = "12345678", description = "текущий пароль", minLength = 8, maxLength = 16)
    private String currentPassword;
    @Schema(example = "87654321", description = "новый пароль", minLength = 8, maxLength = 16)
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
