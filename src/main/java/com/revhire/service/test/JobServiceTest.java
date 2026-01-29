package com.revhire.service.test;

import com.revhire.model.Job;
import com.revhire.service.JobService;
import com.revhire.service.JobServiceImpl;
import java.util.List;

public class JobServiceTest {

    public static void main(String[] args) {
        JobService jobService = new JobServiceImpl();

        // Test: View All Jobs
        List<Job> jobs = jobService.viewAllJobs();
        if (jobs != null) {
            System.out.println("✅ View All Jobs Test Passed. Found: " + jobs.size());
        } else {
            System.out.println("❌ View All Jobs Test Failed");
        }

        // Test: Search Jobs
        List<Job> searchResults = jobService.searchJobs("Developer", null, null, null, null, null);
        if (searchResults != null) {
            System.out.println("✅ Search Jobs Test Passed");
        } else {
            System.out.println("❌ Search Jobs Test Failed");
        }
    }
}
