package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.ExperienceDao;

@ExtendWith(MockitoExtension.class)
public class ExperienceServiceTest {

    @Mock
    private ExperienceDao experienceDao;

    private ExperienceService experienceService;

    @BeforeEach
    public void setup() {
        experienceService = new ExperienceServiceImpl(experienceDao);
    }

    @Test
    public void testAddExperienceSuccess() {
        int jsId = 1;
        String company = "Google";
        String role = "SDE";
        int years = 2;

        when(experienceDao.addExperience(jsId, company, role, years)).thenReturn(true);

        boolean result = experienceService.addExperience(jsId, company, role, years);
        assertTrue(result);
    }

    @Test
    public void testAddExperienceEmptyCompany() {
        int jsId = 1;
        String company = "";
        String role = "SDE";
        int years = 2;

        boolean result = experienceService.addExperience(jsId, company, role, years);
        assertFalse(result);
    }
}
