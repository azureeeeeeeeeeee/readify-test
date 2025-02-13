package com.test.backend.DTO;

import com.test.backend.models.Comment;

public class CommentDTO {
    public Integer id;
    public CustomUserDTO user;
    public String comment;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.user = new CustomUserDTO(comment.getUser());
        this.comment = comment.getComment();
    }
}
