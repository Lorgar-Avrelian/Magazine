package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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
    @Schema(example = "Текст комментария", description = "текст комментария", minLength = 8, maxLength = 64)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
