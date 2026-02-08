package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revhire.dao.EducationDao;

public class EducationServiceTest {

    @Mock
    private EducationDao educationDao;

    @InjectMocks
    private EducationServiceImpl educationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEducationSuccess() {
        when(educationDao.addEducation(1, "BS", "University", 2010, 2014)).thenReturn(true);

        boolean result = educationService.addEducation(1, "BS", "University", 2010, 2014);
        assertTrue(result);
    }

    @Test
    public void testAddEducationInvalid() {
        boolean result = educationService.addEducation(1, "", "University", 2010, 2014);
        assertFalse(result);
        verify(educationDao, never()).addEducation(anyInt(), anyString(), anyString(), anyInt(), anyInt());
    }
}
