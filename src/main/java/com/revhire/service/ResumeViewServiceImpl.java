package com.revhire.service;

import java.util.List;

import com.revhire.dao.ResumeViewDao;
import com.revhire.dao.ResumeViewDaoImpl;

public class ResumeViewServiceImpl implements ResumeViewService {

    private ResumeViewDao dao = new ResumeViewDaoImpl();

    @Override
    public String getObjective(int resumeId) {
        return dao.getObjective(resumeId);
    }

    @Override
    public List<String> getEducation(int resumeId) {
        return dao.getEducation(resumeId);
    }

    @Override
    public List<String> getExperience(int resumeId) {
        return dao.getExperience(resumeId);
    }

    @Override
    public List<String> getSkills(int resumeId) {
        return dao.getSkills(resumeId);
    }

    @Override
    public List<String> getProjects(int resumeId) {
        return dao.getProjects(resumeId);
    }
}
