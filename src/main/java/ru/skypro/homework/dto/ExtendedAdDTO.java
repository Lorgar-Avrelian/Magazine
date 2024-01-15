package ru.skypro.homework.dto;

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
    private int pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private int price;
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
