package com.revhire.ui;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.revhire.model.Job;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;

public class JobMenu {

	private static JobService jobService = new JobServiceImpl();

	public static void showMenu(int companyId) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\n=== Job Menu ===");
			System.out.println("1. Post Job");
			System.out.println("2. View All Jobs");
			System.out.println("3. Back");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				Job job = new Job();
				job.setCompanyId(companyId);

				System.out.print("Job Title: ");
				job.setTitle(sc.nextLine());

				System.out.print("Description: ");
				job.setDescription(sc.nextLine());

				System.out.print("Skills: ");
				job.setSkills(sc.nextLine());

				System.out.print("Min Experience: ");
				job.setMinExperience(sc.nextInt());

				sc.nextLine();
				System.out.print("Location: ");
				job.setLocation(sc.nextLine());

				System.out.print("Salary Min: ");
				job.setSalaryMin(sc.nextDouble());

				System.out.print("Salary Max: ");
				job.setSalaryMax(sc.nextDouble());

				sc.nextLine();
				System.out.print("Job Type: ");
				job.setJobType(sc.nextLine());

				job.setDeadline(Date.valueOf("2026-12-31"));

				if (jobService.postJob(job)) {
					System.out.println("✅ Job posted successfully");
				} else {
					System.out.println("❌ Job posting failed");
				}
				break;

			case 2:
				List<Job> jobs = jobService.viewAllJobs();
				System.out.println("\n--- Available Jobs ---");
				for (Job j : jobs) {
					System.out.println(j.getJobId() + " | " + j.getTitle() + " | " + j.getLocation() + " | "
							+ j.getSalaryMin() + "-" + j.getSalaryMax());
				}
				break;

			case 3:
				return;
			}
		}
	}
}
