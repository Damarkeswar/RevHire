package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.CompanyDao;
import com.revhire.dao.EmployerDao;
import com.revhire.dao.JobSeekerDao;
import com.revhire.dao.UserDao;
import com.revhire.model.User;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private EmployerDao employerDao;
    @Mock
    private CompanyDao companyDao;
    @Mock
    private JobSeekerDao jobSeekerDao;
    @Mock
    private NotificationService notificationService;

    private AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        authService = new AuthServiceImpl(userDao, employerDao, companyDao, jobSeekerDao, notificationService);
    }

    @Test
    public void testLoginSuccess() {
        User mockUser = new User();
        mockUser.setEmail("test@test.com");
        mockUser.setRole("JOB_SEEKER");

        when(userDao.login("test@test.com", "pass123")).thenReturn(mockUser);

        User result = authService.login("test@test.com", "pass123");
        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    public void testLoginFailure() {
        when(userDao.login("wrong@test.com", "pass")).thenReturn(null);
        User result = authService.login("wrong@test.com", "pass");
        assertNull(result);
    }

    @Test
    public void testRegisterJobSeekerSuccess() {
        User user = new User();
        user.setEmail("new@test.com");
        user.setRole("JOB_SEEKER");

        when(userDao.emailExists("new@test.com")).thenReturn(false);
        when(userDao.registerUser(user)).thenReturn(1);
        when(jobSeekerDao.createJobSeeker(1, "New Seeker")).thenReturn(1);

        boolean result = authService.register(user);
        assertTrue(result);
        verify(notificationService).notifyJobSeeker(eq(1), anyString());
    }

    @Test
    public void testRegisterEmployerSuccess() {
        User user = new User();
        user.setEmail("emp@test.com");
        user.setRole("EMPLOYER");

        when(userDao.emailExists("emp@test.com")).thenReturn(false);
        when(userDao.registerUser(user)).thenReturn(2);
        when(employerDao.createEmployer(2)).thenReturn(1);
        when(companyDao.createCompany(any())).thenReturn(1);

        boolean result = authService.register(user);
        assertTrue(result);
        verify(notificationService).notifyEmployer(eq(1), anyString());
    }

    @Test
    public void testRegisterEmailExists() {
        User user = new User();
        user.setEmail("exists@test.com");
        when(userDao.emailExists("exists@test.com")).thenReturn(true);

        boolean result = authService.register(user);
        assertFalse(result);
    }

    @Test
    public void testChangePasswordSuccess() {
        when(userDao.changePassword(1, "newPass")).thenReturn(true);
        boolean result = authService.changePassword(1, "newPass");
        assertTrue(result);
    }

    @Test
    public void testChangePasswordTooShort() {
        boolean result = authService.changePassword(1, "123");
        assertFalse(result);
    }
}
