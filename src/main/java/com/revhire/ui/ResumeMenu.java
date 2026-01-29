package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.ResumeService;
import com.revhire.service.ResumeServiceImpl;

public class ResumeMenu {

    private static ResumeService resumeService = new ResumeServiceImpl();

    public static void show(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Resume Menu ===");
            System.out.println("1. Create Resume (Objective)");
            System.out.println("2. Add Education");
            System.out.println("3. Add Experience");
            System.out.println("4. Add Skills");
            System.out.println("5. Add Projects");
            System.out.println("6. View Resume");
            System.out.println("7. Back");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number.");
                continue;
            }

            int resumeId = resumeService.getActiveResumeId(jobSeekerId);

            switch (choice) {

                case 1:
                    System.out.print("Enter Objective: ");
                    String obj = sc.nextLine();
                    int id = resumeService.createResume(jobSeekerId, obj);
                    if (id > 0)
                        System.out.println("✅ Resume created");
                    break;

                case 2:
                    EducationMenu.add(jobSeekerId);
                    break;

                case 3:
                    ExperienceMenu.add(jobSeekerId);
                    break;

                case 4:
                    SkillMenu.add(jobSeekerId);
                    break;

                case 5:
                    if (resumeId == 0) {
                        System.out.println("❌ Create resume first");
                    } else {
                        ProjectMenu.add(resumeId);
                    }
                    break;

                case 6:
                    if (resumeId == 0) {
                        System.out.println("❌ No resume found");
                    } else {
                        ResumeViewMenu.show(resumeId);
                    }
                    break;

                case 7:
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
