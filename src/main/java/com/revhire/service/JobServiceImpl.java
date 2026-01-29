package com.revhire.service;

import java.util.List;

import com.revhire.dao.JobDao;
import com.revhire.dao.JobDaoImpl;
import com.revhire.model.Job;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobServiceImpl implements JobService {

	private static final Logger logger = LogManager.getLogger(JobServiceImpl.class);
	private JobDao jobDao = new JobDaoImpl();

	@Override
	public boolean postJob(Job job) {

		if (job.getTitle() == null || job.getTitle().isEmpty()) {
			System.out.println("❌ Job title cannot be empty");
			return false;
		}

		boolean result = jobDao.postJob(job);
		if (result) {
			logger.info("Job posted: {} by Company ID: {}", job.getTitle(), job.getCompanyId());
			NotificationService ns = new NotificationServiceImpl();
			ns.notifyEmployer(job.getCompanyId(), "Job posted successfully: " + job.getTitle());

			// Notify matching seekers
			try {
				com.revhire.dao.JobSeekerDao seekerDao = new com.revhire.dao.JobSeekerDaoImpl();
				// Use the first skill and first edu word as a simple match filter
				String skillFilter = null;
				if (job.getSkills() != null && !job.getSkills().isEmpty()) {
					skillFilter = job.getSkills().split(",")[0].trim();
				}
				String eduFilter = null;
				if (job.getEducationRequired() != null && !job.getEducationRequired().isEmpty()) {
					eduFilter = job.getEducationRequired().split(" ")[0].trim();
				}

				List<com.revhire.model.JobSeeker> matchingSeekers = seekerDao.searchJobSeekers(skillFilter, null,
						eduFilter);
				for (com.revhire.model.JobSeeker s : matchingSeekers) {
					ns.notifyJobSeeker(s.getJobSeekerId(),
							"New Job Match: " + job.getTitle() + " at " + job.getLocation());
				}
			} catch (Exception e) {
				logger.error("Error notifying seekers for job match", e);
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
			NotificationService ns = new NotificationServiceImpl();
			ns.notifyEmployer(job.getCompanyId(), "Job updated: " + job.getTitle());
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
		com.revhire.dao.SkillDao skillDao = new com.revhire.dao.SkillDaoImpl();
		com.revhire.dao.EducationDao eduDao = new com.revhire.dao.EducationDaoImpl();

		List<com.revhire.model.Skill> seekerSkills = skillDao.getSkillsByJobSeeker(jobSeekerId);
		List<com.revhire.model.Education> seekerEdu = eduDao.getEducationByJobSeeker(jobSeekerId);

		// Simple matching: any job where skills_required contains one of our skills
		// or education_required matches our degree.
		List<Job> allJobs = jobDao.getAllJobs();
		java.util.List<Job> matches = new java.util.ArrayList<>();

		for (Job job : allJobs) {
			boolean skillMatch = false;
			if (job.getSkills() != null) {
				for (com.revhire.model.Skill s : seekerSkills) {
					if (job.getSkills().toLowerCase().contains(s.getSkillName().toLowerCase())) {
						skillMatch = true;
						break;
					}
				}
			}

			boolean eduMatch = false;
			if (job.getEducationRequired() != null) {
				for (com.revhire.model.Education e : seekerEdu) {
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
