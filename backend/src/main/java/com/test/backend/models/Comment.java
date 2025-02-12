package com.test.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "please specify user id")
    public CustomUser user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @NotNull(message = "please specify review id")
    public Review review;

    @Column(name = "comment")
    @NotNull(message = "comment cant be empty")
    public String comment;

    public Comment(Review review, Integer id, CustomUser user, String comment) {
        this.review = review;
        this.id = id;
        this.user = user;
        this.comment = comment;
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
