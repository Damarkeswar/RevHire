package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.EducationService;
import com.revhire.service.EducationServiceImpl;

public class EducationMenu {

    private static EducationService service =
            new EducationServiceImpl();

    public static void add(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Add Education ===");

        System.out.print("Degree: ");
        String degree = sc.nextLine();

        System.out.print("Institution: ");
        String institution = sc.nextLine();

        System.out.print("Start Year: ");
        int startYear = sc.nextInt();

        System.out.print("End Year: ");
        int endYear = sc.nextInt();

        if (service.addEducation(
                jobSeekerId, degree, institution, startYear, endYear)) {
            System.out.println("✅ Education saved to DB");
        } else {
            System.out.println("❌ Failed to save education");
        }
    }
}
