package com.test.backend.permissions;

import com.test.backend.models.Book;
import com.test.backend.models.CustomUser;
import com.test.backend.utilities.UserUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookPermissions {
    private final UserUtils userUtils;

    public BookPermissions(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public boolean checkCreate(CustomUser user){
        return user.getIsAdmin();
    }

    public boolean checkUpdate(Integer userId, Book book) {
        return userUtils.getAdminStatus() && Objects.equals(userId, book.getAddedBy().getId());

    }

    public boolean checkDelete(Book book) {
        return userUtils.getAdminStatus() && Objects.equals(userUtils.getUserId(), book.getAddedBy().getId());
    }
}
