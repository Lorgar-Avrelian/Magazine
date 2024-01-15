package ru.skypro.homework.dto;

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
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private int pk;
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
