package com.revhire.ui;

import java.util.Scanner;

import com.revhire.dao.CompanyDao;
import com.revhire.dao.CompanyDaoImpl;
import com.revhire.dao.EmployerDao;
import com.revhire.dao.EmployerDaoImpl;
import com.revhire.dao.JobSeekerDao;
import com.revhire.dao.JobSeekerDaoImpl;
import com.revhire.model.Company;
import com.revhire.model.User;
import com.revhire.service.AuthService;
import com.revhire.service.AuthServiceImpl;

public class MainMenu {

	public static void main(String[] args) {

		Scanner sc = com.revhire.config.ScannerUtil.getScanner();
		AuthService authService = new AuthServiceImpl();

		while (true) {
			System.out.println("\n=== RevHire ===");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Enter your choice (1-3): ");

			String input = sc.nextLine();
			int choice;

			try {
				choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("❌ Invalid input. Please enter a number.");
				continue;
			}

			switch (choice) {

				case 1:
					User user = new User();

					System.out.print("Enter Email: ");
					user.setEmail(sc.nextLine());

					System.out.print("Enter Password: ");
					user.setPassword(sc.nextLine());

					System.out.print("Enter Role (JOB_SEEKER / EMPLOYER): ");
					user.setRole(sc.nextLine());

					System.out.print("Enter Security Question: ");
					user.setSecurityQuestion(sc.nextLine());

					System.out.print("Enter Security Answer: ");
					user.setSecurityAnswer(sc.nextLine());

					if (authService.register(user)) {
						System.out.println("✅ Registration successful");
					} else {
						System.out.println("❌ Registration failed");
					}
					break;

				case 2:
					System.out.println("\n1. Login");
					System.out.println("2. Forgot Password");
					System.out.print("Choice: ");
					int loginChoice = Integer.parseInt(sc.nextLine());

					if (loginChoice == 1) {
						System.out.print("Enter Email: ");
						String email = sc.nextLine();

						System.out.print("Enter Password: ");
						String pass = sc.nextLine();

						User loggedIn = authService.login(email, pass);

						if (loggedIn == null) {
							System.out.println("❌ Invalid credentials");
							break;
						}

						System.out.println("✅ Login successful");

						if ("JOB_SEEKER".equalsIgnoreCase(loggedIn.getRole())) {
							JobSeekerDao jsDao = new JobSeekerDaoImpl();
							int jsId = jsDao.getJobSeekerIdByUserId(loggedIn.getUserId());
							if (jsId == 0) {
								System.out.println("❌ Job seeker profile not found.");
								break;
							}
							com.revhire.ui.JobSeekerDashboard.show(jsId);
						} else if ("EMPLOYER".equalsIgnoreCase(loggedIn.getRole())) {
							EmployerDao employerDao = new EmployerDaoImpl();
							int employerId = employerDao.getEmployerIdByUserId(loggedIn.getUserId());
							if (employerId == 0) {
								System.out.println("❌ Employer record not found");
								break;
							}
							CompanyDao companyDao = new CompanyDaoImpl();
							Company company = companyDao.getCompanyByEmployerId(employerId);
							if (company == null) {
								System.out.println("❌ Company not found");
								break;
							}
							com.revhire.ui.EmployerDashboard.show(company.getCompanyId());
						}
					} else if (loginChoice == 2) {
						System.out.print("Enter Registered Email: ");
						String email = sc.nextLine();
						User u = authService.getUserByEmail(email);

						if (u == null) {
							System.out.println("❌ Email not found");
						} else {
							System.out.println("Question: " + u.getSecurityQuestion());
							System.out.print("Your Answer: ");
							String ans = sc.nextLine();

							if (ans.equalsIgnoreCase(u.getSecurityAnswer())) {
								System.out.print("Enter New Password: ");
								String newPass = sc.nextLine();
								if (authService.resetPassword(email, newPass)) {
									System.out.println("✅ Password reset successful");
								} else {
									System.out.println("❌ Failed to reset password");
								}
							} else {
								System.out.println("❌ Incorrect answer");
							}
						}
					}
					break;

				case 3:
					System.out.println("👋 Thank you for using RevHire");
					System.exit(0);
					break;

				default:
					System.out.println("❌ Please enter a valid choice (1-3)");
			}
		}
	}
}
