package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * CreateOrUpdateAd { <br><br>
 * title	string <br>
 * minLength: 4 <br>
 * maxLength: 32 <br>
 * заголовок объявления <br><br>
 *
 * price	integer($int32) <br>
 * minimum: 0 <br>
 * maximum: 10000000 <br>
 * цена объявления <br><br>
 *
 * description	string <br>
 * minLength: 8 <br>
 * maxLength: 64 <br>
 * описание объявления <br>
 * }
 */
public class CreateOrUpdateAdDTO {
    private String title;
    private int price;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
