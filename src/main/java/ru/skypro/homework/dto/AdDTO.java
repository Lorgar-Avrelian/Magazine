package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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
    @Schema(example = "1", description = "id автора объявления")
    private int author;
    @Schema(example = "/user_1_ad_1.png", description = "ссылка на картинку объявления")
    private String image;
    @Schema(example = "1", description = "id объявления")
    private int pk;
    @Schema(example = "1000", description = "цена объявления")
    private int price;
    @Schema(example = "Новое объявление", description = "заголовок объявления")
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
