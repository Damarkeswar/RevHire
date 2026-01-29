package com.revhire.ui;

import java.sql.Date;
import java.util.Scanner;
import com.revhire.model.Job;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;

public class JobPostMenu {

    private static JobService jobService = new JobServiceImpl();

    public static void create(int companyId) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== Create Job Posting ===");

            Job job = new Job();
            job.setCompanyId(companyId);

            System.out.print("Enter Job Title: ");
            job.setTitle(sc.nextLine());

            System.out.print("Enter Description: ");
            job.setDescription(sc.nextLine());

            System.out.print("Enter Skills Required (comma separated): ");
            job.setSkills(sc.nextLine());

            System.out.print("Enter Education Required: ");
            job.setEducationRequired(sc.nextLine());

            try {
                System.out.print("Enter Minimum Experience (years): ");
                job.setMinExperience(Integer.parseInt(sc.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input for experience. Please enter a number.");
                return;
            }

            System.out.print("Enter Location: ");
            job.setLocation(sc.nextLine());

            try {
                System.out.print("Enter Minimum Salary: ");
                job.setSalaryMin(Double.parseDouble(sc.nextLine()));

                System.out.print("Enter Maximum Salary: ");
                job.setSalaryMax(Double.parseDouble(sc.nextLine()));
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input for salary. Please enter a number.");
                return;
            }

            System.out.print("Enter Job Type (Full-time / Part-time / Remote): ");
            job.setJobType(sc.nextLine());

            try {
                System.out.print("Enter Deadline (YYYY-MM-DD): ");
                job.setDeadline(Date.valueOf(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid date format. Please use YYYY-MM-DD.");
                return;
            }

            if (jobService.postJob(job)) {
                System.out.println("✅ Job posted successfully!");
            } else {
                System.out.println("❌ Failed to post job.");
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }

        System.out.println("Press Enter to go back...");
        sc.nextLine();
    }
}
