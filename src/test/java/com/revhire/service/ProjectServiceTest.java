package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.ProjectDao;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectDao projectDao;

    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        projectService = new ProjectServiceImpl(projectDao);
    }

    @Test
    public void testAddProjectSuccess() {
        int resumeId = 10;
        String title = "RevHire";
        String desc = "Job Portal";

        when(projectDao.addProject(resumeId, title, desc)).thenReturn(true);

        boolean result = projectService.addProject(resumeId, title, desc);
        assertTrue(result);
    }

    @Test
    public void testAddProjectInvalidResume() {
        boolean result = projectService.addProject(0, "Title", "Desc");
        assertFalse(result);
    }

    @Test
    public void testAddProjectEmptyTitle() {
        boolean result = projectService.addProject(10, "", "Desc");
        assertFalse(result);
    }
}
