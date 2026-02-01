package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.EducationDao;
import com.revhire.dao.ResumeDao;
import com.revhire.dao.SkillDao;
import com.revhire.model.Education;
import com.revhire.model.Skill;
import com.revhire.model.Company;

@ExtendWith(MockitoExtension.class)
public class ProfileCompletionServiceTest {

    @Mock
    private EducationDao eduDao;

    @Mock
    private SkillDao skillDao;

    @Mock
    private ResumeDao resumeDao;

    private ProfileCompletionService completionService;

    @BeforeEach
    public void setup() {
        completionService = new ProfileCompletionService(eduDao, skillDao, resumeDao);
    }

    @Test
    public void testGetJobSeekerCompletionZero() {
        int jsId = 1;
        when(eduDao.getEducationByJobSeeker(jsId)).thenReturn(Collections.emptyList());
        when(skillDao.getSkillsByJobSeeker(jsId)).thenReturn(Collections.emptyList());
        when(resumeDao.getLatestResumeId(jsId)).thenReturn(0);

        int completion = completionService.getJobSeekerCompletion(jsId);
        assertEquals(25, completion, "Should be 25% for basic profile");
    }

    @Test
    public void testGetJobSeekerCompletionFull() {
        int jsId = 1;
        when(eduDao.getEducationByJobSeeker(jsId)).thenReturn(Arrays.asList(new Education()));
        when(skillDao.getSkillsByJobSeeker(jsId)).thenReturn(Arrays.asList(new Skill()));
        when(resumeDao.getLatestResumeId(jsId)).thenReturn(10);

        int completion = completionService.getJobSeekerCompletion(jsId);
        assertEquals(100, completion, "Should be 100% for full profile");
    }

    @Test
    public void testGetCompanyCompletionNotUpdated() {
        Company company = new Company();
        company.setCompanyName("Not Updated");
        company.setIndustry("Not Updated");
        company.setLocation("Not Updated");
        company.setWebsite("Not Updated");
        company.setDescription("Not Updated");

        int completion = completionService.getCompanyCompletion(company);
        assertEquals(0, completion, "Should be 0% if nothing updated");
    }

    @Test
    public void testGetCompanyCompletionPartial() {
        Company company = new Company();
        company.setCompanyName("RevHire Corp");
        company.setIndustry("IT");
        company.setLocation("Not Updated");
        company.setWebsite("Not Updated");
        company.setDescription("Not Updated");

        int completion = completionService.getCompanyCompletion(company);
        assertEquals(40, completion, "Should be 40% if two fields updated");
    }
}
