package com.revhire.dao;

public interface EducationDao {

    boolean addEducation(
            int jobSeekerId,
            String degree,
            String institution,
            int startYear,
            int endYear);

    java.util.List<com.revhire.model.Education> getEducationByJobSeeker(int jobSeekerId);
}
