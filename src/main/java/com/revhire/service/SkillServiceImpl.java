package com.revhire.service;

import com.revhire.dao.SkillDao;
import com.revhire.dao.SkillDaoImpl;

public class SkillServiceImpl implements SkillService {

    private SkillDao skillDao;

    public SkillServiceImpl() {
        this.skillDao = new SkillDaoImpl();
    }

    public SkillServiceImpl(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    @Override
    public boolean addSkill(int jobSeekerId,
            String skillName,
            String proficiency) {

        if (skillName == null || skillName.isEmpty()) {
            System.out.println("❌ Skill name cannot be empty");
            return false;
        }

        return skillDao.addSkill(
                jobSeekerId, skillName, proficiency);
    }
}
