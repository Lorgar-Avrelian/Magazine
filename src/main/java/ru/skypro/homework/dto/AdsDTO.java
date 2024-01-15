package ru.skypro.homework.dto;

import java.util.List;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Ads { <br><br>
 * count	integer($int32) <br>
 * общее количество объявлений <br><br>
 * results	[ <br>
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
 * }] <br>
 * }
 */
public class AdsDTO {
    private int count;
    private List<AdDTO> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AdDTO> getResults() {
        return results;
    }

    public void setResults(List<AdDTO> results) {
        this.results = results;
    }
}
