package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.ExperienceService;
import com.revhire.service.ExperienceServiceImpl;

public class ExperienceMenu {

    private static ExperienceService service =
            new ExperienceServiceImpl();

    public static void add(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Add Experience ===");

        System.out.print("Company Name: ");
        String company = sc.nextLine();

        System.out.print("Role: ");
        String role = sc.nextLine();

        System.out.print("Years of Experience: ");
        int years = sc.nextInt();

        if (service.addExperience(
                jobSeekerId, company, role, years)) {
            System.out.println("✅ Experience saved to DB");
        } else {
            System.out.println("❌ Failed to save experience");
        }
    }
}
