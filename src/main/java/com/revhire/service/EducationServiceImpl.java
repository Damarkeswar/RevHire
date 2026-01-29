package com.revhire.service;

import com.revhire.dao.EducationDao;
import com.revhire.dao.EducationDaoImpl;

public class EducationServiceImpl implements EducationService {

    private EducationDao educationDao = new EducationDaoImpl();

    @Override
    public boolean addEducation(int jobSeekerId, String degree,
                                String institution, int startYear, int endYear) {

        if (degree == null || degree.isEmpty()) {
            System.out.println("❌ Degree cannot be empty");
            return false;
        }

        return educationDao.addEducation(
            jobSeekerId, degree, institution, startYear, endYear
        );
    }
}
