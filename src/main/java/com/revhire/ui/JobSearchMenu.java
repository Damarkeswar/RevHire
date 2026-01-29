package com.revhire.ui;

import java.util.List;
import java.util.Scanner;

import com.revhire.model.Job;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;

public class JobSearchMenu {

    private static JobService jobService = new JobServiceImpl();

    public static void show(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Job Search ===");

        System.out.print("Job Title (or press Enter): ");
        String title = sc.nextLine();
        title = title.isEmpty() ? null : title;

        System.out.print("Location (or press Enter): ");
        String location = sc.nextLine();
        location = location.isEmpty() ? null : location;

        System.out.print("Min Experience (or 0): ");
        int exp = sc.nextInt();
        Integer minExp = exp == 0 ? null : exp;

        sc.nextLine();
        System.out.print("Company Name (or press Enter): ");
        String company = sc.nextLine();
        company = company.isEmpty() ? null : company;

        System.out.print("Min Salary (or 0): ");
        double sal = sc.nextDouble();
        Double minSalary = sal == 0 ? null : sal;

        sc.nextLine();
        System.out.print("Job Type (FULLTIME / PARTTIME / CONTRACT or Enter): ");
        String jobType = sc.nextLine();
        jobType = jobType.isEmpty() ? null : jobType;

        List<Job> jobs = jobService.searchJobs(
                title, location, minExp, company, minSalary, jobType);

        System.out.println("\n--- Search Results ---");

        if (jobs.isEmpty()) {
            System.out.println("❌ No matching jobs found");
        } else {
            for (Job j : jobs) {
                System.out.println(
                        j.getJobId() + " | " +
                                j.getTitle() + " | " +
                                j.getLocation() + " | " +
                                j.getSalaryMin() + "-" + j.getSalaryMax() +
                                " | " + j.getJobType() +
                                " | Edu: " + j.getEducationRequired());
            }
        }
    }

    public static void showRecommendedJobs(int jobSeekerId) {
        System.out.println("\n=== Recommended Jobs for Your Profile ===");
        List<Job> matchedJobs = jobService.getMatchingJobs(jobSeekerId);

        if (matchedJobs.isEmpty()) {
            System.out.println("❌ No matching jobs found based on your skills and education.");
            System.out.println("💡 Pro-tip: Complete your profile to get better recommendations!");
        } else {
            for (Job j : matchedJobs) {
                System.out.println(
                        j.getJobId() + " | " +
                                j.getTitle() + " | " +
                                j.getLocation() + " | " +
                                "Edu: " + j.getEducationRequired() +
                                " | Skills: " + j.getSkills());
            }
        }
        System.out.println("\nPress Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }
}
