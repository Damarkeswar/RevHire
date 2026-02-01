package com.revhire.ui;

import java.util.Scanner;

public class EmployerDashboard {

    public static void show(int companyId) {

        Scanner sc = com.revhire.config.ScannerUtil.getScanner();

        com.revhire.service.ProfileCompletionService completionService = new com.revhire.service.ProfileCompletionService();
        com.revhire.service.CompanyService companyService = new com.revhire.service.CompanyServiceImpl();

        while (true) {
            com.revhire.model.Company company = companyService.getCompany(companyId);
            int completion = completionService.getCompanyCompletion(company);
            System.out.println("\n=== Employer Dashboard [" + completion + "% Complete] ===");
            if (completion < 100) {
                System.out.println("⚠️ Please update your company profile to attract more applicants.");
            }
            System.out.println("1. Manage Company Profile");
            System.out.println("2. Create Job Posting");
            System.out.println("3. Manage Job Postings");
            System.out.println("4. View Applicants");
            System.out.println("5. Shortlist / Reject Applications");
            System.out.println("6. Search Applicants");
            System.out.println("7. View Notifications");
            System.out.println("8. Logout");
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
                    CompanyProfileMenu.show(companyId);
                    break;
                case 2:
                    JobPostMenu.create(companyId);
                    break;
                case 3:
                    JobManageMenu.show(companyId);
                    break;
                case 4:
                    ApplicantMenu.view(companyId);
                    break;
                case 5:
                    ApplicantMenu.updateStatus(companyId);
                    break;
                case 6:
                    ApplicantSearchMenu.show(companyId);
                    break;
                case 7:
                    NotificationMenu.showEmployer(companyId);
                    break;
                case 8: {
                    System.out.println("👋 Logged out");
                    return;
                }
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
