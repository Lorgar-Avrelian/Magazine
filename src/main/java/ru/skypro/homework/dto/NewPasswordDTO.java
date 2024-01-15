package ru.skypro.homework.dto;

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
    private String currentPassword;
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
