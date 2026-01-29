package com.revhire.dao;

public interface ExperienceDao {

    boolean addExperience(
        int jobSeekerId,
        String companyName,
        String role,
        int years
    );
}
