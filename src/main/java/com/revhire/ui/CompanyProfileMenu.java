package com.revhire.ui;

import java.util.Scanner;
import com.revhire.model.Company;
import com.revhire.service.CompanyService;
import com.revhire.service.CompanyServiceImpl;

public class CompanyProfileMenu {

    private static CompanyService companyService = new CompanyServiceImpl();

    public static void show(int companyId) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            Company company = companyService.getCompany(companyId);
            if (company == null) {
                System.out.println("❌ Company not found.");
                return;
            }

            System.out.println("\n=== Company Profile ===");
            System.out.println("Name: " + company.getCompanyName());
            System.out.println("Industry: " + company.getIndustry());
            System.out.println("Size: " + company.getCompanySize());
            System.out.println("Location: " + company.getLocation());
            System.out.println("Website: " + company.getWebsite());
            System.out.println("Description: " + company.getDescription());

            System.out.println("\n1. Edit Name");
            System.out.println("2. Edit Industry");
            System.out.println("3. Edit Size");
            System.out.println("4. Edit Location");
            System.out.println("5. Edit Website");
            System.out.println("6. Edit Description");
            System.out.println("7. Back");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number between 1 and 7.");
                continue;
            }

            if (choice == 7) {
                // Not closing sc here because System.in shouldn't be closed in a sub-menu
                return;
            }

            System.out.print("Enter new value: ");
            String newValue = sc.nextLine();

            switch (choice) {
                case 1:
                    company.setCompanyName(newValue);
                    break;
                case 2:
                    company.setIndustry(newValue);
                    break;
                case 3:
                    try {
                        company.setCompanySize(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid size. Please enter a valid number.");
                        continue;
                    }
                    break;
                case 4:
                    company.setLocation(newValue);
                    break;
                case 5:
                    company.setWebsite(newValue);
                    break;
                case 6:
                    company.setDescription(newValue);
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
                    continue;
            }

            if (companyService.updateCompany(company)) {
                System.out.println("✅ Profile updated.");
            } else {
                System.out.println("❌ Update failed.");
            }
        }
    }
}
