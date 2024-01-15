package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Ad { <br><br>
 * author	integer($int32) <br>
 * id автора объявления <br><br>
 * image	string <br>
 * ссылка на картинку объявления <br><br>
 * pk	integer($int32) <br>
 * id объявления <br><br>
 * price	integer($int32) <br>
 * цена объявления <br><br>
 * title	string <br>
 * заголовок объявления <br>
 * }
 */
public class AdDTO {
    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
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
