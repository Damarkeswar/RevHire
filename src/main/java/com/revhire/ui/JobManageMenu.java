package com.revhire.ui;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import com.revhire.model.Job;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;

public class JobManageMenu {

    private static JobService jobService = new JobServiceImpl();

    public static void show(int companyId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Manage Job Postings ===");
            List<Job> jobs = jobService.viewJobsByCompany(companyId);

            if (jobs.isEmpty()) {
                System.out.println("No job postings found.");
                return;
            }

            for (Job j : jobs) {
                System.out.println(j.getJobId() + " | " + j.getTitle() + " | " + j.getStatus());
            }

            System.out.println("\n1. Edit Job");
            System.out.println("2. Close / Reopen Job");
            System.out.println("3. Delete Job");
            System.out.println("4. View Statistics");
            System.out.println("5. Back");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number.");
                continue;
            }

            if (choice == 5)
                return;

            int jobId;
            try {
                System.out.print("Enter Job ID: ");
                jobId = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid Job ID. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    editJob(jobId, sc);
                    break;
                case 2:
                    toggleStatus(jobId, sc);
                    break;
                case 3:
                    deleteJob(jobId, sc);
                    break;
                case 4:
                    viewStats(jobId);
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void editJob(int jobId, Scanner sc) {
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            System.out.println("❌ Job not found.");
            return;
        }

        System.out.println("\n--- Editing Job ID: " + jobId + " ---");
        System.out.println("(Leave blank to keep existing value)");

        System.out.print("New Title [" + job.getTitle() + "]: ");
        String title = sc.nextLine();
        if (!title.isEmpty())
            job.setTitle(title);

        System.out.print("New Description [" + job.getDescription() + "]: ");
        String desc = sc.nextLine();
        if (!desc.isEmpty())
            job.setDescription(desc);

        System.out.print("New Education [" + job.getEducationRequired() + "]: ");
        String edu = sc.nextLine();
        if (!edu.isEmpty())
            job.setEducationRequired(edu);

        System.out.print("New Skills [" + job.getSkills() + "]: ");
        String skills = sc.nextLine();
        if (!skills.isEmpty())
            job.setSkills(skills);

        try {
            System.out.print("New Min Experience [" + job.getMinExperience() + "]: ");
            String expStr = sc.nextLine();
            if (!expStr.isEmpty())
                job.setMinExperience(Integer.parseInt(expStr));
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid experience. Keeping old value.");
        }

        System.out.print("New Location [" + job.getLocation() + "]: ");
        String loc = sc.nextLine();
        if (!loc.isEmpty())
            job.setLocation(loc);

        try {
            System.out.print("New Min Salary [" + job.getSalaryMin() + "]: ");
            String minSalStr = sc.nextLine();
            if (!minSalStr.isEmpty())
                job.setSalaryMin(Double.parseDouble(minSalStr));

            System.out.print("New Max Salary [" + job.getSalaryMax() + "]: ");
            String maxSalStr = sc.nextLine();
            if (!maxSalStr.isEmpty())
                job.setSalaryMax(Double.parseDouble(maxSalStr));
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid salary. Keeping old values.");
        }

        System.out.print("New Job Type [" + job.getJobType() + "]: ");
        String type = sc.nextLine();
        if (!type.isEmpty())
            job.setJobType(type);

        try {
            System.out.print("New Deadline (YYYY-MM-DD) [" + job.getDeadline() + "]: ");
            String deadlineStr = sc.nextLine();
            if (!deadlineStr.isEmpty())
                job.setDeadline(Date.valueOf(deadlineStr));
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid date format. Keeping old value.");
        }

        if (jobService.updateJob(job)) {
            System.out.println("✅ Job updated successfully.");
        } else {
            System.out.println("❌ Failed to update job.");
        }
    }

    private static void toggleStatus(int jobId, Scanner sc) {
        Job job = jobService.getJobById(jobId);
        if (job == null)
            return;

        System.out.print("Enter new status (OPEN / CLOSED): ");
        job.setStatus(sc.nextLine().toUpperCase());

        if (jobService.updateJob(job)) {
            System.out.println("✅ Status updated.");
        } else {
            System.out.println("❌ Failed to update status.");
        }
    }

    private static void deleteJob(int jobId, Scanner sc) {
        System.out.print("Are you sure? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            if (jobService.deleteJob(jobId)) {
                System.out.println("✅ Job deleted.");
            } else {
                System.out.println("❌ Failed to delete job.");
            }
        }
    }

    private static void viewStats(int jobId) {
        com.revhire.service.ApplicationService appService = new com.revhire.service.ApplicationServiceImpl();
        java.util.Map<String, Integer> stats = appService.getStatisticsByJob(jobId);

        System.out.println("\n--- Job Statistics for ID: " + jobId + " ---");
        if (stats.isEmpty()) {
            System.out.println("No applications received yet.");
        } else {
            int total = 0;
            for (String status : stats.keySet()) {
                int count = stats.get(status);
                System.out.println(status + ": " + count);
                total += count;
            }
            System.out.println("---------------------------");
            System.out.println("TOTAL APPLICATIONS: " + total);
        }
    }
}
