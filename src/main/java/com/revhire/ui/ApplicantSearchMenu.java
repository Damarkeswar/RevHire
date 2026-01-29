package com.revhire.ui;

import java.util.List;
import java.util.Scanner;
import com.revhire.dao.JobSeekerDao;
import com.revhire.dao.JobSeekerDaoImpl;
import com.revhire.model.JobSeeker;

public class ApplicantSearchMenu {

    private static JobSeekerDao seekerDao = new JobSeekerDaoImpl();

    public static void show(int companyId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Search Applicants ===");
        System.out.print("Enter skills to search (or enter to skip): ");
        String skills = sc.nextLine();
        if (skills.isEmpty())
            skills = null;

        System.out.print("Enter education keyword (or enter to skip): ");
        String edu = sc.nextLine();
        if (edu.isEmpty())
            edu = null;

        List<JobSeeker> results = seekerDao.searchJobSeekers(skills, null, edu);

        if (results.isEmpty()) {
            System.out.println("No matching applicants found.");
        } else {
            System.out.println("\nFound " + results.size() + " matches:");
            for (JobSeeker js : results) {
                System.out.println("ID: " + js.getJobSeekerId() + " | Name: " + js.getFullName());
            }
        }

        System.out.println("\nPress Enter to go back...");
        sc.nextLine();
    }
}
