package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.revhire.model.Job;
import java.util.List;

public class JobServiceTest {

    private JobService jobService = new JobServiceImpl();

    @Test
    public void testViewAllJobs() {
        List<Job> jobs = jobService.viewAllJobs();
        assertNotNull(jobs, "Jobs list should not be null");
    }

    @Test
    public void testGetJobByIdInvalid() {
        Job job = jobService.getJobById(-1);
        assertNull(job, "Invalid job ID should return null");
    }
}
