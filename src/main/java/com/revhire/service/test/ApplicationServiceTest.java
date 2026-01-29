package com.revhire.service.test;

import com.revhire.model.Application;
import com.revhire.service.ApplicationService;
import com.revhire.service.ApplicationServiceImpl;
import java.util.List;

public class ApplicationServiceTest {

    public static void main(String[] args) {
        ApplicationService appService = new ApplicationServiceImpl();

        // Test: View My Applications (using dummy ID 1)
        List<Application> apps = appService.viewMyApplications(1);
        if (apps != null) {
            System.out.println("✅ View My Applications Test Passed");
        } else {
            System.out.println("❌ View My Applications Test Failed");
        }
    }
}
