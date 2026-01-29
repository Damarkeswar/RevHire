package com.revhire.ui;

import java.util.List;

import com.revhire.service.ResumeViewService;
import com.revhire.service.ResumeViewServiceImpl;

public class ResumeViewMenu {

    private static ResumeViewService service =
            new ResumeViewServiceImpl();

    public static void show(int resumeId) {

        System.out.println("\n=== Resume Details ===");
        System.out.println("Resume ID: " + resumeId);

        System.out.println("\nObjective:");
        System.out.println(service.getObjective(resumeId));

        System.out.println("\nEducation:");
        printList(service.getEducation(resumeId));

        System.out.println("\nExperience:");
        printList(service.getExperience(resumeId));

        System.out.println("\nSkills:");
        printList(service.getSkills(resumeId));

        System.out.println("\nProjects:");
        printList(service.getProjects(resumeId));
    }

    private static void printList(List<String> list) {
        if (list.isEmpty()) {
            System.out.println("Not added");
        } else {
            for (String s : list) {
                System.out.println("- " + s);
            }
        }
    }
}
