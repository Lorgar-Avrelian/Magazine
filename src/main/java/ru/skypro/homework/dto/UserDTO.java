package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * User { <br><br>
 * id	integer($int32) <br>
 * id пользователя <br><br>
 * email	string <br>
 * логин пользователя <br><br>
 * firstName	string <br>
 * имя пользователя <br><br>
 * lastName	string <br>
 * фамилия пользователя <br><br>
 * phone	string <br>
 * телефон пользователя <br><br>
 * {@link Role}	string <br>
 * роль пользователя <br><br>
 * image	string <br>
 * ссылка на аватар пользователя <br>
 * }
 */
public class UserDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
