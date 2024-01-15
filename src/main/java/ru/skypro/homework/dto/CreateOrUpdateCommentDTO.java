package ru.skypro.homework.dto;

/**
 * DTO <br>
 * <hr>
 * <br>
 * CreateOrUpdateComment { <br><br>
 * text*	string <br>
 * minLength: 8 <br>
 * maxLength: 64 <br>
 * текст комментария <br>
 * }
 */
public class CreateOrUpdateCommentDTO {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
