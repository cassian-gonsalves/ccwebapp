package com.neu.ccwebapp.domain;

import org.apache.logging.log4j.message.Message;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Book {



    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID id;

    @NotNull(message = "Cannot be null")
    private String title;

    @NotNull(message = "Cannot be null")
    private String author;

    @NotNull(message = "Cannot be null")
    private String isbn;

    @Min(value = 0, message = "The value must be positive")
    @NotNull(message = "Cannot be null")
    private int quantity;




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
