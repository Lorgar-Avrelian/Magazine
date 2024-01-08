package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer pk;
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private String text;
    private Integer adPk;

    public Comment() {
    }

    public Comment(Integer pk, User author, String authorImage, String authorFirstName, long createdAt, String text, Integer adPk) {
        this.pk = pk;
        this.author = author;
        this.authorImage = authorImage;
        this.authorFirstName = authorFirstName;
        this.createdAt = createdAt;
        this.text = text;
        this.adPk = adPk;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    @JsonBackReference
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAdPk() {
        return adPk;
    }

    public void setAdPk(Integer adPk) {
        this.adPk = adPk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return createdAt == comment.createdAt && Objects.equals(pk, comment.pk) && Objects.equals(author, comment.author) && Objects.equals(authorImage, comment.authorImage) && Objects.equals(authorFirstName, comment.authorFirstName) && Objects.equals(text, comment.text) && Objects.equals(adPk, comment.adPk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, author, authorImage, authorFirstName, createdAt, text, adPk);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", author=" + author +
                ", authorImage='" + authorImage + '\'' +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", adPk=" + adPk +
                '}';
    }
}