package com.neu.ccwebapp.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Book {

    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID id;

    @NotNull(message = "Title cannot be null")
    @NotBlank
    private String title;

    @NotNull(message = "Author cannot be null")
    @NotBlank
    private String author;

    @NotNull(message = "ISBN cannot be null")
    @NotBlank
    private String isbn;

    @Min(value = 0, message = "The quantity must be positive")
    @NotNull(message = "Quantity cannot be null")
    private int quantity;






    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Image image;


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
