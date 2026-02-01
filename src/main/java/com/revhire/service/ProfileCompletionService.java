package com.revhire.service;

import com.revhire.dao.*;

public class ProfileCompletionService {

    private EducationDao eduDao;
    private SkillDao skillDao;
    private ResumeDao resumeDao;

    public ProfileCompletionService() {
        this.eduDao = new EducationDaoImpl();
        this.skillDao = new SkillDaoImpl();
        this.resumeDao = new ResumeDaoImpl();
    }

    public ProfileCompletionService(EducationDao eduDao, SkillDao skillDao, ResumeDao resumeDao) {
        this.eduDao = eduDao;
        this.skillDao = skillDao;
        this.resumeDao = resumeDao;
    }

    public int getJobSeekerCompletion(int jobSeekerId) {
        int completion = 0;

        // 1. Basic Profile (25%)
        // We'll assume if they have a record they have basic info, maybe check phone
        completion += 25;

        // 2. Education (25%)
        if (!eduDao.getEducationByJobSeeker(jobSeekerId).isEmpty()) {
            completion += 25;
        }

        // 3. Skills (25%)
        if (!skillDao.getSkillsByJobSeeker(jobSeekerId).isEmpty()) {
            completion += 25;
        }

        // 4. Resume (25%)
        if (resumeDao.getLatestResumeId(jobSeekerId) > 0) {
            completion += 25;
        }

        return completion;
    }

    public int getCompanyCompletion(com.revhire.model.Company company) {
        if (company == null)
            return 0;
        int completion = 0;
        if (company.getCompanyName() != null && !company.getCompanyName().equals("Not Updated"))
            completion += 20;
        if (company.getIndustry() != null && !company.getIndustry().equals("Not Updated"))
            completion += 20;
        if (company.getLocation() != null && !company.getLocation().equals("Not Updated"))
            completion += 20;
        if (company.getWebsite() != null && !company.getWebsite().equals("Not Updated"))
            completion += 20;
        if (company.getDescription() != null && !company.getDescription().equals("Not Updated"))
            completion += 20;
        return completion;
    }
}
