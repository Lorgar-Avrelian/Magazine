package ru.skypro.homework.dto;

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
    private String username;
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
