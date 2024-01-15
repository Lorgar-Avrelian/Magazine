package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * UpdateUser { <br><br>
 * firstName	string <br>
 * minLength: 3 <br>
 * maxLength: 10 <br>
 * имя пользователя <br><br>
 * lastName	string <br>
 * minLength: 3 <br>
 * maxLength: 10 <br>
 * фамилия пользователя <br><br>
 * phone	string <br>
 * pattern: \+7\s?\(?\d{3}\)?\s?\d{3}-?\d{2}-?\d{2} <br>
 * телефон пользователя <br>
 * }
 */
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String phone;

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
}
