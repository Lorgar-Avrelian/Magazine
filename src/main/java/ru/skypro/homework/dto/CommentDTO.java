package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Comment { <br><br>
 * author	integer($int32) <br>
 * id автора комментария <br><br>
 * authorImage	string <br>
 * ссылка на аватар автора комментария <br><br>
 * authorFirstName	string <br>
 * имя создателя комментария <br><br>
 * createdAt	integer($int64) <br>
 * дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970 <br><br>
 * pk	integer($int32) <br>
 * id комментария <br><br>
 * text	string <br>
 * текст комментария <br>
 * }
 */
public class CommentDTO {
    @Schema(example = "1", description = "id автора комментария")
    private int author;
    @Schema(example = "/user_1.png", description = "ссылка на аватар автора комментария")
    private String authorImage;
    @Schema(example = "Username", description = "имя создателя комментария")
    private String authorFirstName;
    @Schema(example = "100000000", description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private long createdAt;
    @Schema(example = "1", description = "id комментария")
    private int pk;
    @Schema(example = "Текст комментария", description = "текст комментария")
    private String text;

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
