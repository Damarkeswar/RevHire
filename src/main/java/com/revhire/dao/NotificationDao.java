package com.revhire.dao;

import java.util.List;

public interface NotificationDao {

    void createNotification(int jobSeekerId, String message);

    List<String> getNotifications(int jobSeekerId);

    void markAllAsRead(int jobSeekerId);

    void createEmployerNotification(int companyId, String message);

    List<String> getEmployerNotifications(int companyId);

    void markEmployerAsRead(int companyId);
}
