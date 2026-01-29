package com.revhire.service;

import com.revhire.model.Job;
import com.revhire.model.Skill;
import com.revhire.model.Education;
import com.revhire.dao.SkillDao;
import com.revhire.dao.EducationDao;
import com.revhire.dao.JobDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Mock
    private JobDao jobDao;

    @Mock
    private SkillDao skillDao;

    @Mock
    private EducationDao educationDao;

    @InjectMocks
    private JobServiceImpl jobService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMatchingJobs_SkillMatch() {
        int seekerId = 1;
        List<Skill> skills = new ArrayList<>();
        Skill s = new Skill();
        s.setSkillName("Java");
        skills.add(s);

        when(skillDao.getSkillsByJobSeeker(seekerId)).thenReturn(skills);
        when(educationDao.getEducationByJobSeeker(seekerId)).thenReturn(new ArrayList<>());

        List<Job> allJobs = new ArrayList<>();
        Job j = new Job();
        j.setTitle("Java Developer");
        j.setSkills("Java, Spring");
        allJobs.add(j);

        when(jobDao.getAllJobs()).thenReturn(allJobs);

        List<Job> matches = jobService.getMatchingJobs(seekerId);

        assertFalse(matches.isEmpty());
        assertEquals("Java Developer", matches.get(0).getTitle());
    }

    @Test
    public void testGetMatchingJobs_NoMatch() {
        int seekerId = 1;
        when(skillDao.getSkillsByJobSeeker(seekerId)).thenReturn(new ArrayList<>());
        when(educationDao.getEducationByJobSeeker(seekerId)).thenReturn(new ArrayList<>());

        List<Job> allJobs = new ArrayList<>();
        Job j = new Job();
        j.setSkills("Python");
        allJobs.add(j);

        when(jobDao.getAllJobs()).thenReturn(allJobs);

        List<Job> matches = jobService.getMatchingJobs(seekerId);

        assertTrue(matches.isEmpty());
    }
}
