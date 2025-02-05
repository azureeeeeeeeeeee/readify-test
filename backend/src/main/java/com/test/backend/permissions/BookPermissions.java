package com.test.backend.permissions;

import com.test.backend.models.CustomUser;
import org.springframework.stereotype.Service;

@Service
public class BookPermissions {
    public boolean create(CustomUser user) {
        return user.getIsAdmin();
    }
}
