package com.revhire.ui;

import java.util.List;
import java.util.Scanner;
import com.revhire.model.Application;
import com.revhire.model.Job;
import com.revhire.service.ApplicationService;
import com.revhire.service.ApplicationServiceImpl;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;

public class ApplicantMenu {

    private static ApplicationService appService = new ApplicationServiceImpl();
    private static JobService jobService = new JobServiceImpl();

    public static void view(int companyId) {
        Scanner sc = new Scanner(System.in);
        List<Job> jobs = jobService.viewJobsByCompany(companyId);

        if (jobs.isEmpty()) {
            System.out.println("No job postings found.");
            return;
        }

        System.out.println("\n=== Your Jobs ===");
        for (Job j : jobs) {
            System.out.println(j.getJobId() + " | " + j.getTitle());
        }

        int jobId;
        try {
            System.out.print("\nEnter Job ID to view applicants: ");
            jobId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Job ID.");
            return;
        }

        // Security check
        Job selectedJob = jobService.getJobById(jobId);
        if (selectedJob == null || selectedJob.getCompanyId() != companyId) {
            System.out.println("❌ Access denied. This job does not belong to your company.");
            return;
        }

        List<Application> apps = appService.getApplicationsByJob(jobId);

        if (apps.isEmpty()) {
            System.out.println("No applicants for this job.");
            return;
        }

        System.out.println("\n=== Applicants for Job " + jobId + " ===");
        for (Application a : apps) {
            System.out.println("App ID: " + a.getApplicationId() + " | Seeker ID: " + a.getJobSeekerId() + " | Status: "
                    + a.getStatus());
        }
    }

    public static void updateStatus(int companyId) {
        Scanner sc = new Scanner(System.in);
        int appId;
        try {
            System.out.print("\nEnter Application ID to update: ");
            appId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Application ID.");
            return;
        }

        // Security Check
        // I implemented getApplicationById in ApplicationDao but not Service yet.
        // Let's use the Dao directly or add to service.

        com.revhire.dao.ApplicationDao appDao = new com.revhire.dao.ApplicationDaoImpl();
        Application selectedApp = appDao.getApplicationById(appId);

        if (selectedApp == null) {
            System.out.println("❌ Application not found.");
            return;
        }

        Job job = jobService.getJobById(selectedApp.getJobId());
        if (job == null || job.getCompanyId() != companyId) {
            System.out.println("❌ Access denied. This application is not for your company's jobs.");
            return;
        }

        System.out.print("Enter Status (SHORTLISTED / REJECTED): ");
        String status = sc.nextLine().toUpperCase();

        System.out.print("Enter Comments: ");
        String comment = sc.nextLine();

        if (appService.changeStatus(appId, status, comment)) {
            System.out.println("✅ Application status updated successfully.");
        } else {
            System.out.println("❌ Failed to update status.");
        }
    }
}
