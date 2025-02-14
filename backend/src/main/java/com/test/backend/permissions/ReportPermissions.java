package com.test.backend.permissions;

import com.test.backend.DTO.AuthResult;
import com.test.backend.utilities.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class ReportPermissions {
    private final UserUtils userUtils;

    public ReportPermissions(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public AuthResult checkFindAll() {
        if (!userUtils.getAdminStatus()) {
            return new AuthResult(false, "You are not authorized");
        }
        return new AuthResult(true, null);
    }

    public AuthResult checkResolve() {
        if (!userUtils.getAdminStatus()) {
            return new AuthResult(false, "You are not authorized");
        }
        return new AuthResult(true, null);
    }
}
