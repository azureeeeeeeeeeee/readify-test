package com.test.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {
    @Id
    public String isbn;

    @Column(name = "title")
    @NotNull(message = "title is required")
    public String title;

    @Column(name = "description")
    @NotNull(message = "description is required")
    public String description;

    @Column(name = "image_url")
    @NotNull(message = "image url is required")
    public String imageURL;

    @ManyToOne
    @JoinColumn(name = "added_by")
    public CustomUser addedBy;

    public Book(String imageURL, String isbn, String title, String description, CustomUser addedBy) {
        this.imageURL = imageURL;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.addedBy = addedBy;
    }

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public CustomUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(CustomUser addedBy) {
        this.addedBy = addedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
