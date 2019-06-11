package com.neu.ccwebapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Image {



    @Id
    @Column(length = 16)
    private UUID idBook;

    @NotNull(message = "Title cannot be null")
    @NotBlank
    private String imageUrl;


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Book book;



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
