package com.revhire.service;

import com.revhire.dao.ExperienceDao;
import com.revhire.dao.ExperienceDaoImpl;

public class ExperienceServiceImpl implements ExperienceService {

    private ExperienceDao experienceDao =
            new ExperienceDaoImpl();

    @Override
    public boolean addExperience(int jobSeekerId,
                                 String companyName,
                                 String role,
                                 int years) {

        if (companyName == null || companyName.isEmpty()) {
            System.out.println("❌ Company name cannot be empty");
            return false;
        }

        return experienceDao.addExperience(
            jobSeekerId, companyName, role, years
        );
    }
}
