package com.neu.ccwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Image
{
    @Id
    @Column(length = 16)
    private UUID imageId;

    @NotNull(message = "Title cannot be null")
    @NotBlank
    @Column(length = 65535, columnDefinition="TEXT")
    private String url;

    @NotNull
    @JsonIgnore
    private String fileName;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Book book;

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
