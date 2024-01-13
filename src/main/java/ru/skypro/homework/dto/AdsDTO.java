package ru.skypro.homework.dto;

import java.util.List;

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
