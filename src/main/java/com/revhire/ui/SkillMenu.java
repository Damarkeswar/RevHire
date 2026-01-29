package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.SkillService;
import com.revhire.service.SkillServiceImpl;

public class SkillMenu {

    private static SkillService service =
            new SkillServiceImpl();

    public static void add(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Add Skill ===");

        System.out.print("Skill Name: ");
        String skill = sc.nextLine();

        System.out.print("Proficiency (Beginner/Intermediate/Expert): ");
        String level = sc.nextLine();

        if (service.addSkill(jobSeekerId, skill, level)) {
            System.out.println("✅ Skill saved to DB");
        } else {
            System.out.println("❌ Failed to save skill");
        }
    }
}
