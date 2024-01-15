package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Register { <br><br>
 * username	string <br>
 * minLength: 4 <br>
 * maxLength: 32 <br>
 * логин <br><br>
 * password	string <br>
 * minLength: 8 <br>
 * maxLength: 16 <br>
 * пароль <br><br>
 * firstName	string <br>
 * minLength: 2 <br>
 * maxLength: 16 <br>
 * имя пользователя <br><br>
 * lastName	string <br>
 * minLength: 2 <br>
 * maxLength: 16 <br>
 * фамилия пользователя <br><br>
 * phone	string <br>
 * pattern: \+7\s?\(?\d{3}\)?\s?\d{3}-?\d{2}-?\d{2}
 * телефон пользователя <br><br>
 * role	string <br>
 * роль пользователя <br><br>
 * Enum: <br>
 * {@link Role} <br>
 * }
 */
public class RegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
