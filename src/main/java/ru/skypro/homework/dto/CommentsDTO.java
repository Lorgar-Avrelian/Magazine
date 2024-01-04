package ru.skypro.homework.dto;

import java.util.List;

public class CommentsDTO {
    private int count;
    private List<CommentDTO> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentDTO> getResult() {
        return result;
    }

    public void setResult(List<CommentDTO> result) {
        this.result = result;
    }
}
