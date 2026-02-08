package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revhire.dao.ResumeDao;

public class ResumeServiceTest {

    @Mock
    private ResumeDao resumeDao;

    @InjectMocks
    private ResumeServiceImpl resumeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateResumeSuccess() {
        when(resumeDao.createResume(1, "Objective")).thenReturn(100);

        int resumeId = resumeService.createResume(1, "Objective");
        assertEquals(100, resumeId);
    }

    @Test
    public void testCreateResumeEmptyObjective() {
        int resumeId = resumeService.createResume(1, "");
        assertEquals(0, resumeId, "Should fail validation");
        verify(resumeDao, never()).createResume(anyInt(), anyString());
    }

    @Test
    public void testGetActiveResumeId() {
        when(resumeDao.getLatestResumeId(1)).thenReturn(50);
        int id = resumeService.getActiveResumeId(1);
        assertEquals(50, id);
    }
}
