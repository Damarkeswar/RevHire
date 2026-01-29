package com.revhire.service;

public interface SkillService {

    boolean addSkill(
        int jobSeekerId,
        String skillName,
        String proficiency
    );
}
