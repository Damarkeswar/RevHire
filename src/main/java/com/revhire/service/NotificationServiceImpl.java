package com.revhire.service;

import java.util.List;

import com.revhire.dao.NotificationDao;
import com.revhire.dao.NotificationDaoImpl;

public class NotificationServiceImpl implements NotificationService {

    private NotificationDao notificationDao;

    public NotificationServiceImpl() {
        this.notificationDao = new NotificationDaoImpl();
    }

    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public void notifyJobSeeker(int jobSeekerId, String message) {
        notificationDao.createNotification(jobSeekerId, message);
    }

    @Override
    public List<String> viewNotifications(int jobSeekerId) {

        List<String> list = notificationDao.getNotifications(jobSeekerId);

        notificationDao.markAllAsRead(jobSeekerId);
        return list;
    }

    @Override
    public void notifyEmployer(int companyId, String message) {
        notificationDao.createEmployerNotification(companyId, message);
    }

    @Override
    public List<String> viewEmployerNotifications(int companyId) {
        List<String> list = notificationDao.getEmployerNotifications(companyId);
        notificationDao.markEmployerAsRead(companyId);
        return list;
    }
}
