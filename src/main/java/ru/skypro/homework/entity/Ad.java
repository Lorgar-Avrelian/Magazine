package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * DAO <br>
 * <hr>
 * <br>
 * Ad { <br><br>
 * Integer pk <br>
 * id автора объявления <br><br>
 * {@link User} author <br>
 * автор объявления <br><br>
 * String image <br>
 * ссылка на картинку объявления <br><br>
 * int price <br>
 * цена объявления <br><br>
 * String title <br>
 * заголовок объявления <br><br>
 * String description <br>
 * описание объявления <br>
 * }
 */
@Entity
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer pk;
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    private String image;
    private int price;
    private String title;
    private String description;

    public Ad() {
    }

    public Ad(Integer pk, User author, String image, int price, String title, String description) {
        this.pk = pk;
        this.author = author;
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return price == ad.price && Objects.equals(pk, ad.pk) && Objects.equals(author, ad.author) && Objects.equals(image, ad.image) && Objects.equals(title, ad.title) && Objects.equals(description, ad.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, author, image, price, title, description);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "pk=" + pk +
                ", author=" + author +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
