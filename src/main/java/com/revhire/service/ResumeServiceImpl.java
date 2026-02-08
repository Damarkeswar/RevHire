package com.revhire.service;

import com.revhire.dao.ResumeDao;
import com.revhire.dao.ResumeDaoImpl;

public class ResumeServiceImpl implements ResumeService {

    private ResumeDao resumeDao;

    public ResumeServiceImpl() {
        this.resumeDao = new ResumeDaoImpl();
    }

    public ResumeServiceImpl(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    @Override
    public int createResume(int jobSeekerId, String objective) {

        if (objective == null || objective.isEmpty()) {
            System.out.println("❌ Objective cannot be empty");
            return 0;
        }

        return resumeDao.createResume(jobSeekerId, objective);
    }

    @Override
    public int getActiveResumeId(int jobSeekerId) {
        return resumeDao.getLatestResumeId(jobSeekerId);
    }
}
