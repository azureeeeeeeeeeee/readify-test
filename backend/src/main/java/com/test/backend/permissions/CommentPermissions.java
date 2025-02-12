package com.test.backend.permissions;

import com.test.backend.DTO.AuthResult;
import com.test.backend.models.Comment;
import com.test.backend.utilities.UserUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentPermissions {
    private final UserUtils userUtils;

    public CommentPermissions(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public AuthResult checkCreate() {
        return new AuthResult(true, null); // authorize as long as user is authenticated (already done with middleware)
    }

    public AuthResult checkDelete(Comment comment) {
        if (!userUtils.getAdminStatus() && !Objects.equals(comment.getUser().getId(), userUtils.getUserId())) {
            return new AuthResult(false, "You are not authorized");
        }

        return new AuthResult(true, null);
    }

    public AuthResult checkUpdate(Comment comment) {
        if (comment.getUser().getId() != userUtils.getUserId()) {
            return new AuthResult(false, "You aare not authorized");
        }

        return new AuthResult(true, null);
    }
}
