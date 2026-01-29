package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.revhire.model.User;

public class AuthServiceTest {

    private AuthService authService = new AuthServiceImpl();

    @Test
    public void testLoginInvalid() {
        User user = authService.login("invalid@test.com", "wrongpass");
        assertNull(user, "Invalid login should return null");
    }

    @Test
    public void testLoginEmpty() {
        User user = authService.login("", "");
        assertNull(user, "Empty credentials should return null");
    }
}
