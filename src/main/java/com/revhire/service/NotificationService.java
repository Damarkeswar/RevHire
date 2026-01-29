package com.revhire.service;

import java.util.List;

public interface NotificationService {

    void notifyJobSeeker(int jobSeekerId, String message);

    List<String> viewNotifications(int jobSeekerId);

    void notifyEmployer(int companyId, String message);

    List<String> viewEmployerNotifications(int companyId);
}
