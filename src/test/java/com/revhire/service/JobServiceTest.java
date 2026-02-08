package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revhire.dao.EducationDao;
import com.revhire.dao.JobDao;
import com.revhire.dao.JobSeekerDao;
import com.revhire.dao.SkillDao;
import com.revhire.model.Education;
import com.revhire.model.Job;
import com.revhire.model.JobSeeker;
import com.revhire.model.Skill;

public class JobServiceTest {

    @Mock
    private JobDao jobDao;

    @Mock
    private JobSeekerDao jsDao;

    @Mock
    private SkillDao skillDao;

    @Mock
    private EducationDao eduDao;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private JobServiceImpl jobService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewAllJobs() {
        List<Job> mockJobs = new ArrayList<>();
        when(jobDao.getAllJobs()).thenReturn(mockJobs);

        List<Job> jobs = jobService.viewAllJobs();
        assertNotNull(jobs, "Jobs list should not be null");
        assertEquals(mockJobs, jobs);
    }

    @Test
    public void testGetJobByIdInvalid() {
        when(jobDao.getJobById(-1)).thenReturn(null);

        Job job = jobService.getJobById(-1);
        assertNull(job, "Invalid job ID should return null");
    }

    @Test
    public void testPostJobWithMatching() {
        // Prepare Job
        Job job = new Job();
        job.setCompanyId(1);
        job.setTitle("Java Dev");
        job.setSkills("Java");

        // Prepare Seeker
        JobSeeker js = new JobSeeker();
        js.setJobSeekerId(101);

        Skill skill = new Skill();
        skill.setSkillName("Java");

        // Mock behavior
        when(jobDao.postJob(job)).thenReturn(true);
        when(jsDao.getAllJobSeekers()).thenReturn(Collections.singletonList(js));
        when(skillDao.getSkillsByJobSeeker(101)).thenReturn(Collections.singletonList(skill));

        boolean result = jobService.postJob(job);

        assertTrue(result);

        // Verify notification to employer
        verify(notificationService).notifyEmployer(eq(1), contains("posted successfully"));

        // Verify notification to matched seeker
        verify(notificationService).notifyJobSeeker(eq(101), contains("matching your profile"));
    }

    @Test
    public void testGetMatchingJobs() {
        int seekerId = 101;

        // Seeker Skills
        Skill skill = new Skill();
        skill.setSkillName("Java");
        when(skillDao.getSkillsByJobSeeker(seekerId)).thenReturn(Collections.singletonList(skill));
        when(eduDao.getEducationByJobSeeker(seekerId)).thenReturn(Collections.emptyList());

        // All Jobs
        Job matchJob = new Job();
        matchJob.setJobId(1);
        matchJob.setSkills("Java, SQL");

        Job noMatchJob = new Job();
        noMatchJob.setJobId(2);
        noMatchJob.setSkills("Python");

        when(jobDao.getAllJobs()).thenReturn(List.of(matchJob, noMatchJob));

        List<Job> matches = jobService.getMatchingJobs(seekerId);

        assertEquals(1, matches.size());
        assertEquals(1, matches.get(0).getJobId());
    }
}
