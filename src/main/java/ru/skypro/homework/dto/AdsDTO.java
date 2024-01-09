package ru.skypro.homework.dto;

import java.util.List;

public class AdsDTO {
    private int count;
    private List<AdDTO> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AdDTO> getResult() {
        return result;
    }

    public void setResult(List<AdDTO> result) {
        this.result = result;
    }
}
