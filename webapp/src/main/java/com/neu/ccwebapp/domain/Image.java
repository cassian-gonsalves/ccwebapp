package com.neu.ccwebapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Image {



    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID idBook;

    @NotNull(message = "Title cannot be null")
    @NotBlank
    private String imageUrl;


    @OneToOne
    @MapsId
    private Book book;


    public Image(){}


    public Image(@NotNull(message = "Title cannot be null") @NotBlank String imageUrl, Book book) {
        this.imageUrl = imageUrl;
        this.book = book;
    }

    public UUID getIdBook() {
        return idBook;
    }

    public void setIdBook(UUID idBook) {
        this.idBook = idBook;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
