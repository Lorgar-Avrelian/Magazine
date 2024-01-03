package ru.skypro.homework.dto;

import java.util.List;

public class Ads {
    private int count;
    private List<Ad> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Ad> getResult() {
        return result;
    }

    public void setResult(List<Ad> result) {
        this.result = result;
    }
}
