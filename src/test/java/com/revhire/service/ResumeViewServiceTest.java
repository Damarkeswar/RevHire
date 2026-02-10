package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.ResumeViewDao;

@ExtendWith(MockitoExtension.class)
public class ResumeViewServiceTest {

    @Mock
    private ResumeViewDao dao;

    private ResumeViewService service;

    @BeforeEach
    public void setup() {
        service = new ResumeViewServiceImpl(dao);
    }

    @Test
    public void testGetObjective() {
        when(dao.getObjective(1)).thenReturn("My Objective");
        assertEquals("My Objective", service.getObjective(1));
    }

    @Test
    public void testGetEducation() {
        List<String> list = Arrays.asList("Edu 1");
        when(dao.getEducation(1)).thenReturn(list);
        assertEquals(1, service.getEducation(1).size());
    }

    @Test
    public void testGetExperience() {
        List<String> list = Arrays.asList("Exp 1");
        when(dao.getExperience(1)).thenReturn(list);
        assertEquals(1, service.getExperience(1).size());
    }

    @Test
    public void testGetSkills() {
        List<String> list = Arrays.asList("Java");
        when(dao.getSkills(1)).thenReturn(list);
        assertEquals(1, service.getSkills(1).size());
    }

    @Test
    public void testGetProjects() {
        List<String> list = Arrays.asList("Project X");
        when(dao.getProjects(1)).thenReturn(list);
        assertEquals(1, service.getProjects(1).size());
    }
}
