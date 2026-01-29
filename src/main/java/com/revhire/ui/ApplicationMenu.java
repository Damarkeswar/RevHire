package com.revhire.ui;

import java.util.List;
import java.util.Scanner;

import com.revhire.model.Application;
import com.revhire.service.ApplicationService;
import com.revhire.service.ApplicationServiceImpl;

public class ApplicationMenu {

    private static ApplicationService appService = new ApplicationServiceImpl();

    // ---------------- APPLY ----------------
    public static void apply(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Apply for Job ===");
        int jobId;
        try {
            System.out.print("Enter Job ID: ");
            jobId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Job ID.");
            return;
        }

        Application app = new Application();
        app.setJobId(jobId);
        app.setJobSeekerId(jobSeekerId);

        if (appService.applyForJob(app)) {
            System.out.println("✅ Job applied successfully");
        } else {
            System.out.println("❌ Failed to apply");
        }
    }

    // ---------------- VIEW ----------------
    public static void view(int jobSeekerId) {

        System.out.println("\n=== My Applications ===");

        List<Application> apps = appService.viewMyApplications(jobSeekerId);

        if (apps.isEmpty()) {
            System.out.println("No applications found");
            return;
        }

        for (Application a : apps) {
            System.out.println(
                    "Application ID: " + a.getApplicationId() +
                            " | Job ID: " + a.getJobId() +
                            " | Status: " + a.getStatus());
        }
    }

    // ---------------- WITHDRAW ----------------
    public static void withdraw(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Withdraw Application ===");

        // 1. Show applications
        List<Application> apps = appService.viewMyApplications(jobSeekerId);

        if (apps.isEmpty()) {
            System.out.println("❌ No applications to withdraw");
            return;
        }

        for (Application a : apps) {
            System.out.println(
                    a.getApplicationId() +
                            " | Job ID: " + a.getJobId() +
                            " | Status: " + a.getStatus());
        }

        // 2. Select application
        int appId;
        try {
            System.out.print("\nEnter Application ID to withdraw: ");
            appId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Application ID.");
            return;
        }

        // 3. Confirmation
        System.out.print("Are you sure you want to withdraw? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("❌ Withdrawal cancelled");
            return;
        }

        // 4. Optional reason
        System.out.print("Enter reason (optional): ");
        String reason = sc.nextLine();

        // 5. Withdraw
        if (appService.withdraw(appId, reason)) {
            System.out.println("✅ Application withdrawn successfully");
        } else {
            System.out.println("❌ Withdrawal failed");
        }
    }
}
