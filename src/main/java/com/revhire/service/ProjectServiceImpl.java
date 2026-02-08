package com.revhire.service;

import com.revhire.dao.ProjectDao;
import com.revhire.dao.ProjectDaoImpl;

public class ProjectServiceImpl implements ProjectService {

    private ProjectDao projectDao;

    public ProjectServiceImpl() {
        this.projectDao = new ProjectDaoImpl();
    }

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public boolean addProject(int resumeId,
            String title,
            String description) {

        if (resumeId <= 0) {
            System.out.println("❌ Invalid resume");
            return false;
        }

        if (title == null || title.isEmpty()) {
            System.out.println("❌ Project title cannot be empty");
            return false;
        }

        return projectDao.addProject(resumeId, title, description);
    }
}
