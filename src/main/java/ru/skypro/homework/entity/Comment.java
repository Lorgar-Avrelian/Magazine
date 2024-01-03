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
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private int pk;
    private String text;
    private Integer adId;

    public Comment() {
    }

    public Comment(Integer id, User author, String authorImage, String authorFirstName, long createdAt, int pk, String text, Integer adId) {
        this.id = id;
        this.author = author;
        this.authorImage = authorImage;
        this.authorFirstName = authorFirstName;
        this.createdAt = createdAt;
        this.pk = pk;
        this.text = text;
        this.adId = adId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return createdAt == comment.createdAt && pk == comment.pk && Objects.equals(id, comment.id) && Objects.equals(author, comment.author) && Objects.equals(authorImage, comment.authorImage) && Objects.equals(authorFirstName, comment.authorFirstName) && Objects.equals(text, comment.text) && Objects.equals(adId, comment.adId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, authorImage, authorFirstName, createdAt, pk, text, adId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", authorImage='" + authorImage + '\'' +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", createdAt=" + createdAt +
                ", pk=" + pk +
                ", text='" + text + '\'' +
                ", adId=" + adId +
                '}';
    }
}
