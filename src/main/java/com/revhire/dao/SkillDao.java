package com.revhire.dao;

public interface SkillDao {

    boolean addSkill(
            int jobSeekerId,
            String skillName,
            String proficiency);

    java.util.List<com.revhire.model.Skill> getSkillsByJobSeeker(int jobSeekerId);
}
