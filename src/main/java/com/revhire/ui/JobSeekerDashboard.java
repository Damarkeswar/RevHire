package com.revhire.ui;

import java.util.Scanner;

public class JobSeekerDashboard {

    public static void show(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        com.revhire.service.ProfileCompletionService completionService = new com.revhire.service.ProfileCompletionService();

        while (true) {
            int completion = completionService.getJobSeekerCompletion(jobSeekerId);
            System.out.println("\n=== Job Seeker Dashboard [" + completion + "% Complete] ===");
            if (completion < 100) {
                System.out.println("⚠️ Please complete your profile to improve job matches.");
            }
            System.out.println("1. Manage Profile");
            System.out.println("2. Manage Resume");
            System.out.println("3. Search Jobs");
            System.out.println("4. Apply for Job");
            System.out.println("5. View My Applications");
            System.out.println("6. Withdraw Application");
            System.out.println("7. View Notifications");
            System.out.println("8. View Recommended Jobs");
            System.out.println("9. Logout");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    ProfileMenu.show(jobSeekerId);
                    break;
                case 2:
                    ResumeMenu.show(jobSeekerId);
                    break;
                case 3:
                    JobSearchMenu.show(jobSeekerId);
                    break;
                case 4:
                    ApplicationMenu.apply(jobSeekerId);
                    break;
                case 5:
                    ApplicationMenu.view(jobSeekerId);
                    break;
                case 6:
                    ApplicationMenu.withdraw(jobSeekerId);
                    break;
                case 7:
                    NotificationMenu.show(jobSeekerId);
                    break;
                case 8:
                    JobSearchMenu.showRecommendedJobs(jobSeekerId);
                    break;
                case 9: {
                    System.out.println("👋 Logged out");
                    return;
                }
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
