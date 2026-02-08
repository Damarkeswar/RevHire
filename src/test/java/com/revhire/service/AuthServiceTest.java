package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.revhire.model.User;
import com.revhire.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginInvalid() {
        // Mock behavior: login returns null for invalid credentials
        // Note: The service itself calls userDao.login if input is valid.
        // We simulate userDao return null.
        when(userDao.login("invalid@test.com", "wrongpass")).thenReturn(null);

        User user = authService.login("invalid@test.com", "wrongpass");
        assertNull(user, "Invalid login should return null");
    }

    @Test
    public void testLoginEmpty() {
        // This hits the validation block in service, assumes no DAO call
        User user = authService.login("", "");
        assertNull(user, "Empty credentials should return null");
    }
}
