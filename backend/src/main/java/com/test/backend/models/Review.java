package com.test.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @NotNull
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    public CustomUser user_id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull
    public Book book_id;

    @Column(name = "rating")
    @NotNull
    @NotBlank
    public Integer rating;

    @Column(name = "comment")
    @NotNull
    @NotBlank
    public String comment;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    public LocalDateTime createdAt;

    public Review(Integer id, String comment, Integer rating, Book book_id, CustomUser user_id) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.book_id = book_id;
        this.user_id = user_id;
    }

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public CustomUser getUser_id() {
        return user_id;
    }

    public void setUser_id(CustomUser user_id) {
        this.user_id = user_id;
    }

    public Book getBook_id() {
        return book_id;
    }

    public void setBook_id(Book book_id) {
        this.book_id = book_id;
    }
}
