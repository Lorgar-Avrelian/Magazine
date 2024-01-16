package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO <br>
 * <hr>
 * <br>
 * ExtendedAd { <br><br>
 * pk	integer($int32) <br>
 * id объявления <br><br>
 * authorFirstName	string <br>
 * имя автора объявления <br><br>
 * authorLastName	string <br>
 * фамилия автора объявления <br><br>
 * description	string <br>
 * описание объявления <br><br>
 * email	string <br>
 * логин автора объявления <br><br>
 * image	string <br>
 * ссылка на картинку объявления <br><br>
 * phone	string <br>
 * телефон автора объявления <br><br>
 * price	integer($int32) <br>
 * цена объявления <br><br>
 * title	string <br>
 * заголовок объявления <br>
 * }
 */
public class ExtendedAdDTO {
    @Schema(example = "1", description = "id объявления")
    private int pk;
    @Schema(example = "Иван", description = "имя автора объявления")
    private String authorFirstName;
    @Schema(example = "Иванов", description = "фамилия автора объявления")
    private String authorLastName;
    @Schema(example = "Описание объявления", description = "описание объявления")
    private String description;
    @Schema(example = "user@test.com", description = "логин автора объявления")
    private String email;
    @Schema(example = "/user_1.png", description = "ссылка на картинку объявления")
    private String image;
    @Schema(example = "+79998887766", description = "телефон автора объявления")
    private String phone;
    @Schema(example = "10000", description = "цена объявления")
    private int price;
    @Schema(example = "Заголовок объявления", description = "заголовок объявления")
    private String title;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
