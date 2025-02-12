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
    @NotNull(message = "User id has to be present")
    public CustomUser user;

    public Review review;

    public String comment;
}
