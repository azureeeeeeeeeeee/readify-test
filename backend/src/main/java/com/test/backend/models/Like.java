package com.test.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public CustomUser user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    public Review review;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Like(Integer id, LocalDateTime createdAt, Review review, CustomUser user) {
        this.id = id;
        this.createdAt = createdAt;
        this.review = review;
        this.user = user;
    }

    public Like() {
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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }
}
