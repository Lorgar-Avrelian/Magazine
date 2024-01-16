package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO <br>
 * <hr>
 * <br>
 * CreateOrUpdateAd { <br><br>
 * title	string <br>
 * minLength: 4 <br>
 * maxLength: 32 <br>
 * заголовок объявления <br><br>
 * <p>
 * price	integer($int32) <br>
 * minimum: 0 <br>
 * maximum: 10000000 <br>
 * цена объявления <br><br>
 * <p>
 * description	string <br>
 * minLength: 8 <br>
 * maxLength: 64 <br>
 * описание объявления <br>
 * }
 */
public class CreateOrUpdateAdDTO {
    @Schema(example = "Заголовок объявления", description = "заголовок объявления", minLength = 4, maxLength = 32)
    private String title;
    @Schema(example = "100000", description = "цена объявления", minimum = "0", maximum = "10000000")
    private int price;
    @Schema(example = "Описание объявления", description = "описание объявления", minLength = 8, maxLength = 64)
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
