package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revhire.dao.ApplicationDao;
import com.revhire.dao.ResumeDao;
import com.revhire.model.Application;
import com.revhire.model.Job;

public class ApplicationServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private ResumeDao resumeDao;

    @Mock
    private NotificationService notificationService;

    @Mock
    private JobService jobService;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyForJobSuccess() {
        Application app = new Application();
        app.setJobSeekerId(101);
        app.setJobId(202);

        // Mock resume handling
        when(resumeDao.getLatestResumeId(101)).thenReturn(500);
        when(applicationDao.applyJob(app)).thenReturn(true);

        // Mock job retrieval for notification
        Job mockJob = new Job();
        mockJob.setCompanyId(303);
        mockJob.setTitle("Software Engineer");
        when(jobService.getJobById(202)).thenReturn(mockJob);

        boolean result = applicationService.applyForJob(app);

        assertTrue(result, "Application should succeed when resume exists");
        assertEquals(500, app.getResumeId(), "Resume ID should be set on application");

        // Verify notifications
        verify(notificationService).notifyJobSeeker(eq(101), contains("successfully applied"));
        verify(notificationService).notifyEmployer(eq(303), contains("New application received"));
    }

    @Test
    public void testApplyForJobNoResume() {
        Application app = new Application();
        app.setJobSeekerId(101);

        when(resumeDao.getLatestResumeId(101)).thenReturn(0);

        boolean result = applicationService.applyForJob(app);

        assertFalse(result, "Application should fail if no resume exists");
        verify(applicationDao, never()).applyJob(any(Application.class));
    }

    @Test
    public void testWithdrawApplication() {
        int appId = 1001;
        Application app = new Application();
        app.setJobSeekerId(101);
        app.setJobId(202);

        when(applicationDao.getApplicationById(appId)).thenReturn(app);
        when(applicationDao.withdrawApplication(eq(appId), anyString())).thenReturn(true);
        when(jobService.getJobById(202)).thenReturn(new Job());

        boolean result = applicationService.withdraw(appId, "Not interested");

        assertTrue(result, "Withdrawal should succeed");
        verify(notificationService).notifyJobSeeker(eq(101), contains("WITHDRAWN"));
    }

    @Test
    public void testChangeStatus() {
        int appId = 1001;
        when(applicationDao.updateApplicationStatus(appId, "HIRED", "Good")).thenReturn(true);
        when(applicationDao.getJobSeekerIdByApplicationId(appId)).thenReturn(101);

        boolean result = applicationService.changeStatus(appId, "HIRED", "Good");

        assertTrue(result);
        verify(notificationService).notifyJobSeeker(eq(101), contains("status has been updated"));
    }
}
