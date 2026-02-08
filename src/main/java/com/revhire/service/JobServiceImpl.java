package com.revhire.service;

import java.util.List;

import com.revhire.dao.JobDao;
import com.revhire.dao.JobDaoImpl;
import com.revhire.model.Job;
import com.revhire.dao.EducationDao;
import com.revhire.dao.EducationDaoImpl;
import com.revhire.dao.JobSeekerDao;
import com.revhire.dao.JobSeekerDaoImpl;
import com.revhire.dao.SkillDao;
import com.revhire.dao.SkillDaoImpl;
import com.revhire.model.Education;
import com.revhire.model.JobSeeker;
import com.revhire.model.Skill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobServiceImpl implements JobService {

	private static final Logger logger = LogManager.getLogger(JobServiceImpl.class);
	private JobDao jobDao;
	private JobSeekerDao jsDao;
	private SkillDao skillDao;
	private EducationDao eduDao;
	private NotificationService notificationService;

	public JobServiceImpl() {
		this.jobDao = new JobDaoImpl();
		this.jsDao = new JobSeekerDaoImpl();
		this.skillDao = new SkillDaoImpl();
		this.eduDao = new EducationDaoImpl();
		this.notificationService = new NotificationServiceImpl();
	}

	public JobServiceImpl(JobDao jobDao, JobSeekerDao jsDao, SkillDao skillDao, EducationDao eduDao,
			NotificationService notificationService) {
		this.jobDao = jobDao;
		this.jsDao = jsDao;
		this.skillDao = skillDao;
		this.eduDao = eduDao;
		this.notificationService = notificationService;
	}

	@Override
	public boolean postJob(Job job) {

		if (job.getTitle() == null || job.getTitle().isEmpty()) {
			System.out.println("❌ Job title cannot be empty");
			return false;
		}

		boolean result = jobDao.postJob(job);
		if (result) {
			logger.info("Job posted: {} by Company ID: {}", job.getTitle(), job.getCompanyId());
			notificationService.notifyEmployer(job.getCompanyId(), "Job posted successfully: " + job.getTitle());

			// Notify matching job seekers
			List<JobSeeker> allSeekers = jsDao.getAllJobSeekers();
			for (JobSeeker js : allSeekers) {
				boolean match = false;

				// Skill match
				if (job.getSkills() != null) {
					List<Skill> jsSkills = skillDao.getSkillsByJobSeeker(js.getJobSeekerId());
					for (Skill s : jsSkills) {
						if (job.getSkills().toLowerCase().contains(s.getSkillName().toLowerCase())) {
							match = true;
							break;
						}
					}
				}

				// If not matched by skills, check education
				if (!match && job.getEducationRequired() != null) {
					List<Education> jsEdus = eduDao.getEducationByJobSeeker(js.getJobSeekerId());
					for (Education e : jsEdus) {
						if (job.getEducationRequired().toLowerCase().contains(e.getDegree().toLowerCase())) {
							match = true;
							break;
						}
					}
				}

				if (match) {
					notificationService.notifyJobSeeker(js.getJobSeekerId(),
							"A new job matching your profile was posted: " + job.getTitle());
				}
			}
		} else {
			logger.error("Failed to post job: {}", job.getTitle());
		}
		return result;
	}

	@Override
	public List<Job> viewAllJobs() {
		return jobDao.getAllJobs();
	}

	@Override
	public List<Job> searchJobs(
			String title,
			String location,
			Integer minExperience,
			String companyName,
			Double minSalary,
			String jobType) {

		return jobDao.searchJobs(
				title, location, minExperience, companyName, minSalary, jobType);
	}

	@Override
	public List<Job> viewJobsByCompany(int companyId) {
		return jobDao.getJobsByCompanyId(companyId);
	}

	@Override
	public boolean updateJob(Job job) {
		boolean updated = jobDao.updateJob(job);
		if (updated) {
			logger.info("Job updated: ID {} - {}", job.getJobId(), job.getTitle());
			notificationService.notifyEmployer(job.getCompanyId(), "Job updated: " + job.getTitle());
		} else {
			logger.warn("Failed to update job: ID {}", job.getJobId());
		}
		return updated;
	}

	@Override
	public boolean deleteJob(int jobId) {
		return jobDao.deleteJob(jobId);
	}

	@Override
	public Job getJobById(int jobId) {
		return jobDao.getJobById(jobId);
	}

	@Override
	public List<Job> getMatchingJobs(int jobSeekerId) {
		List<Skill> seekerSkills = skillDao.getSkillsByJobSeeker(jobSeekerId);
		List<Education> seekerEdu = eduDao.getEducationByJobSeeker(jobSeekerId);

		// Simple matching: any job where skills_required contains one of our skills
		// or education_required matches our degree.
		List<Job> allJobs = jobDao.getAllJobs();
		java.util.List<Job> matches = new java.util.ArrayList<>();

		for (Job job : allJobs) {
			boolean skillMatch = false;
			if (job.getSkills() != null) {
				for (Skill s : seekerSkills) {
					if (job.getSkills().toLowerCase().contains(s.getSkillName().toLowerCase())) {
						skillMatch = true;
						break;
					}
				}
			}

			boolean eduMatch = false;
			if (job.getEducationRequired() != null) {
				for (Education e : seekerEdu) {
					if (job.getEducationRequired().toLowerCase().contains(e.getDegree().toLowerCase())) {
						eduMatch = true;
						break;
					}
				}
			}

			if (skillMatch || eduMatch) {
				matches.add(job);
			}
		}
		return matches;
	}
}
