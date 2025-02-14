package com.test.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public CustomUser user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    public Review review;

    @Column(name = "content")
    public String content;

    @Column(name = "is_resolved")
    @JsonProperty("is_resolved")
    public Boolean isResolved;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Report(Integer id, CustomUser user, Review review, String content, Boolean isResolved, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.review = review;
        this.content = content;
        this.isResolved = isResolved;
        this.createdAt = createdAt;
    }

    public Report() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
