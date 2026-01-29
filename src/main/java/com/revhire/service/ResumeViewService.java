package com.revhire.service;

import java.util.List;

public interface ResumeViewService {

    String getObjective(int resumeId);

    List<String> getEducation(int resumeId);

    List<String> getExperience(int resumeId);

    List<String> getSkills(int resumeId);

    List<String> getProjects(int resumeId);
}
