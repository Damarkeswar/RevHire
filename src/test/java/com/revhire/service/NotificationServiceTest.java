package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.NotificationDao;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationDao notificationDao;

    private NotificationService notificationService;

    @BeforeEach
    public void setup() {
        notificationService = new NotificationServiceImpl(notificationDao);
    }

    @Test
    public void testNotifyJobSeeker() {
        int jsId = 1;
        String msg = "Hello";
        notificationService.notifyJobSeeker(jsId, msg);
        verify(notificationDao).createNotification(jsId, msg);
    }

    @Test
    public void testViewNotifications() {
        int jsId = 1;
        List<String> logs = Arrays.asList("Msg 1", "Msg 2");
        when(notificationDao.getNotifications(jsId)).thenReturn(logs);

        List<String> result = notificationService.viewNotifications(jsId);
        assertEquals(2, result.size());
        verify(notificationDao).markAllAsRead(jsId);
    }

    @Test
    public void testNotifyEmployer() {
        int compId = 100;
        String msg = "New Job";
        notificationService.notifyEmployer(compId, msg);
        verify(notificationDao).createEmployerNotification(compId, msg);
    }

    @Test
    public void testViewEmployerNotifications() {
        int compId = 100;
        List<String> logs = Arrays.asList("Emp Msg");
        when(notificationDao.getEmployerNotifications(compId)).thenReturn(logs);

        List<String> result = notificationService.viewEmployerNotifications(compId);
        assertEquals(1, result.size());
        verify(notificationDao).markEmployerAsRead(compId);
    }
}
