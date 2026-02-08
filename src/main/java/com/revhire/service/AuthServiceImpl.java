package com.revhire.service;

import com.revhire.dao.CompanyDao;
import com.revhire.dao.CompanyDaoImpl;
import com.revhire.dao.EmployerDao;
import com.revhire.dao.EmployerDaoImpl;
import com.revhire.dao.UserDao;
import com.revhire.dao.UserDaoImpl;
import com.revhire.model.Company;
import com.revhire.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private UserDao userDao;

    public AuthServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean register(User user) {

        // 1. Check email uniqueness
        if (userDao.emailExists(user.getEmail())) {
            System.out.println("❌ Email already registered");
            return false;
        }

        // 2. Insert user
        int userId = userDao.registerUser(user);

        if (userId <= 0) {
            logger.error("Registration failed for email: {}", user.getEmail());
            System.out.println("❌ Registration failed");
            return false;
        }
        logger.info("User registered successfully: {} with role: {}", user.getEmail(), user.getRole());

        // 3. Role-specific creation
        NotificationService ns = new NotificationServiceImpl();
        if ("EMPLOYER".equalsIgnoreCase(user.getRole())) {

            EmployerDao employerDao = new EmployerDaoImpl();
            int employerId = employerDao.createEmployer(userId);

            if (employerId <= 0) {
                System.out.println("❌ Employer creation failed");
                return false;
            }

            Company company = new Company();
            company.setEmployerId(employerId);
            company.setCompanyName("Not Updated");
            company.setIndustry("Not Updated");
            company.setCompanySize(0);
            company.setDescription("Not Updated");
            company.setWebsite("Not Updated");
            company.setLocation("Not Updated");

            CompanyDao companyDao = new CompanyDaoImpl();
            int companyId = companyDao.createCompany(company);
            if (companyId > 0) {
                ns.notifyEmployer(companyId, "Welcome to RevHire! Please complete your company profile.");
            }
        } else if ("JOB_SEEKER".equalsIgnoreCase(user.getRole())) {
            com.revhire.dao.JobSeekerDao jsDao = new com.revhire.dao.JobSeekerDaoImpl();
            int jsId = jsDao.createJobSeeker(userId, "New Seeker");
            if (jsId > 0) {
                ns.notifyJobSeeker(jsId,
                        "Welcome to RevHire! Start by completing your profile and uploading a resume.");
            }
        }

        System.out.println("✅ Registration successful");
        return true;
    }

    @Override
    public User login(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {
            logger.warn("Login attempt with empty credentials");
            System.out.println("❌ Invalid login credentials");
            return null;
        }

        User user = userDao.login(email, password);
        if (user != null) {
            logger.info("User logged in: {} (Role: {})", email, user.getRole());
        } else {
            logger.warn("Failed login attempt for email: {}", email);
        }
        return user;
    }

    @Override
    public boolean changePassword(int userId, String newPassword) {

        if (newPassword.length() < 4) {
            System.out.println("❌ Password too short");
            return false;
        }

        return userDao.changePassword(userId, newPassword);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        return userDao.resetPassword(email, newPassword);
    }
}
