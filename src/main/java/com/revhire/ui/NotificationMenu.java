package com.revhire.ui;

import java.util.List;
import java.util.Scanner;

import com.revhire.config.ScannerUtil;
import com.revhire.service.NotificationService;
import com.revhire.service.NotificationServiceImpl;

public class NotificationMenu {

    private static NotificationService service = new NotificationServiceImpl();

    public static void show(int jobSeekerId) {
        Scanner sc = ScannerUtil.getScanner();
        List<String> notifications = service.viewNotifications(jobSeekerId);

        System.out.println("\n=== Notifications ===");

        if (notifications.isEmpty()) {
            System.out.println("No notifications");
        } else {
            for (String msg : notifications) {
                System.out.println("• " + msg);
            }
        }
        System.out.println("\nPress Enter to go back...");
        sc.nextLine();
    }

    public static void showEmployer(int companyId) {
        Scanner sc = ScannerUtil.getScanner();
        List<String> notifications = service.viewEmployerNotifications(companyId);

        System.out.println("\n=== Employer Notifications ===");

        if (notifications.isEmpty()) {
            System.out.println("No notifications");
        } else {
            for (String msg : notifications) {
                System.out.println("• " + msg);
            }
        }
        System.out.println("\nPress Enter to go back...");
        sc.nextLine();
    }

}
