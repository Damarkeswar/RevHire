package com.revhire.service;

public interface ExperienceService {

    boolean addExperience(
        int jobSeekerId,
        String companyName,
        String role,
        int years
    );
}
