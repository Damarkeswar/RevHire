package com.revhire.service;

public interface EducationService {

    boolean addEducation(
        int jobSeekerId,
        String degree,
        String institution,
        int startYear,
        int endYear
    );
}
