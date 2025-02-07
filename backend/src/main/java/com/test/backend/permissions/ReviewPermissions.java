package com.test.backend.permissions;

import com.test.backend.DTO.AuthResult;
import com.test.backend.models.Book;
import com.test.backend.models.Review;
import com.test.backend.utilities.UserUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReviewPermissions {
    private final UserUtils userUtils;

    public ReviewPermissions(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public AuthResult checkCreate(Book book) {
        if (userUtils.getUserId() == book.getAddedBy().getId()) {
            return new AuthResult(false, "you cant review your own book");
        }

        return new AuthResult(true, null);
    }
}
