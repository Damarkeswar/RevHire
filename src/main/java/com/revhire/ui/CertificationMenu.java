package com.revhire.ui;

import java.util.Scanner;

import com.revhire.service.CertificationService;
import com.revhire.service.CertificationServiceImpl;

public class CertificationMenu {

    private static CertificationService service =
            new CertificationServiceImpl();

    public static void add(int jobSeekerId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Add Certification ===");

        System.out.print("Certification Name: ");
        String name = sc.nextLine();

        System.out.print("Issued By: ");
        String issuedBy = sc.nextLine();

        System.out.print("Year: ");
        int year = sc.nextInt();

        if (service.addCertification(jobSeekerId, name, issuedBy, year)) {
            System.out.println("✅ Certification saved to DB");
        } else {
            System.out.println("❌ Failed to save certification");
        }
    }
}
