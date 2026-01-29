package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.ProjectService;
import com.revhire.service.ProjectServiceImpl;

public class ProjectMenu {

    private static ProjectService service =
            new ProjectServiceImpl();

    public static void add(int resumeId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Add Project ===");

        System.out.print("Project Title: ");
        String title = sc.nextLine();

        System.out.print("Description: ");
        String desc = sc.nextLine();

        if (service.addProject(resumeId, title, desc)) {
            System.out.println("✅ Project saved to DB");
        } else {
            System.out.println("❌ Failed to save project");
        }
    }
}
