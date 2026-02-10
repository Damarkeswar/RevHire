package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.SkillDao;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @Mock
    private SkillDao skillDao;

    private SkillService skillService;

    @BeforeEach
    public void setup() {
        skillService = new SkillServiceImpl(skillDao);
    }

    @Test
    public void testAddSkillSuccess() {
        int jsId = 1;
        String skill = "Java";
        String prof = "Expert";

        when(skillDao.addSkill(jsId, skill, prof)).thenReturn(true);

        boolean result = skillService.addSkill(jsId, skill, prof);
        assertTrue(result);
    }

    @Test
    public void testAddSkillEmptyName() {
        boolean result = skillService.addSkill(1, "", "Expert");
        assertFalse(result);
    }
}
