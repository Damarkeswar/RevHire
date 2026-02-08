package com.revhire.service;

import java.util.List;

import com.revhire.dao.ApplicationDao;
import com.revhire.dao.ApplicationDaoImpl;
import com.revhire.dao.ResumeDao;
import com.revhire.dao.ResumeDaoImpl;
import com.revhire.model.Application;

public class ApplicationServiceImpl implements ApplicationService {

	private ApplicationDao applicationDao;
	private ResumeDao resumeDao;
	private NotificationService notificationService;
	private JobService jobService;

	public ApplicationServiceImpl() {
		this.applicationDao = new ApplicationDaoImpl();
		this.resumeDao = new ResumeDaoImpl();
		this.notificationService = new NotificationServiceImpl();
		this.jobService = new JobServiceImpl();
	}

	public ApplicationServiceImpl(ApplicationDao applicationDao, ResumeDao resumeDao,
			NotificationService notificationService, JobService jobService) {
		this.applicationDao = applicationDao;
		this.resumeDao = resumeDao;
		this.notificationService = notificationService;
		this.jobService = jobService;
	}

	@Override
	public boolean applyForJob(Application application) {

		int resumeId = resumeDao.getLatestResumeId(application.getJobSeekerId());

		if (resumeId == 0) {
			System.out.println("❌ No resume found. Please create a resume first.");
			return false;
		}

		application.setResumeId(resumeId);
		notificationService.notifyJobSeeker(
				application.getJobSeekerId(),
				"You have successfully applied for Job ID: " + application.getJobId());

		boolean result = applicationDao.applyJob(application);
		if (result) {
			com.revhire.model.Job job = jobService.getJobById(application.getJobId());
			if (job != null) {
				notificationService.notifyEmployer(job.getCompanyId(),
						"New application received for Job: " + job.getTitle());
			}
		}
		return result;
	}

	@Override
	public List<Application> viewMyApplications(int jobSeekerId) {
		return applicationDao.getApplicationsByJobSeeker(jobSeekerId);
	}

	@Override
	public boolean changeStatus(int applicationId, String status, String comment) {
		boolean updated = applicationDao.updateApplicationStatus(applicationId, status, comment);
		if (updated) {
			int jsId = applicationDao.getJobSeekerIdByApplicationId(applicationId);
			if (jsId > 0) {
				notificationService.notifyJobSeeker(jsId, "Your application status has been updated to: " + status);
			}
		}
		return updated;
	}

	@Override
	public boolean withdraw(int applicationId, String reason) {

		if (applicationId <= 0) {
			System.out.println("❌ Invalid application ID");
			return false;
		}

		Application app = applicationDao.getApplicationById(applicationId);
		if (app == null) {
			System.out.println("❌ Application not found");
			return false;
		}

		if (reason == null || reason.isEmpty()) {
			reason = "Withdrawn by candidate";
		}

		boolean updated = applicationDao.withdrawApplication(applicationId, reason);

		if (updated) {
			notificationService.notifyJobSeeker(app.getJobSeekerId(),
					"Your application (ID: " + applicationId + ") has been WITHDRAWN");

			com.revhire.model.Job job = jobService.getJobById(app.getJobId());
			if (job != null) {
				notificationService.notifyEmployer(job.getCompanyId(),
						"Application withdrawn for Job: " + job.getTitle());
			}
		}

		return updated;
	}

	@Override
	public List<Application> getApplicationsByJob(int jobId) {
		return applicationDao.getApplicationsByJob(jobId);
	}

	@Override
	public java.util.Map<String, Integer> getStatisticsByJob(int jobId) {
		return applicationDao.getStatisticsByJob(jobId);
	}
}
