package com.revhire.service;

import java.util.List;

import com.revhire.dao.ApplicationDao;
import com.revhire.dao.ApplicationDaoImpl;
import com.revhire.dao.ResumeDao;
import com.revhire.dao.ResumeDaoImpl;
import com.revhire.model.Application;

public class ApplicationServiceImpl implements ApplicationService {

	private ApplicationDao applicationDao = new ApplicationDaoImpl();

	@Override
	public boolean applyForJob(Application application) {

		ResumeDao resumeDao = new ResumeDaoImpl();

		int resumeId = resumeDao.getLatestResumeId(application.getJobSeekerId());

		if (resumeId == 0) {
			System.out.println("❌ No resume found. Please create a resume first.");
			return false;
		}

		application.setResumeId(resumeId);
		NotificationService ns = new NotificationServiceImpl();
		ns.notifyJobSeeker(
				application.getJobSeekerId(),
				"You have successfully applied for Job ID: " + application.getJobId());

		boolean result = applicationDao.applyJob(application);
		if (result) {
			JobService jobService = new JobServiceImpl();
			com.revhire.model.Job job = jobService.getJobById(application.getJobId());
			if (job != null) {
				ns.notifyEmployer(job.getCompanyId(), "New application received for Job: " + job.getTitle());
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
				NotificationService ns = new NotificationServiceImpl();
				ns.notifyJobSeeker(jsId, "Your application status has been updated to: " + status);
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
			NotificationService ns = new NotificationServiceImpl();
			ns.notifyJobSeeker(app.getJobSeekerId(), "Your application (ID: " + applicationId + ") has been WITHDRAWN");

			JobService jobService = new JobServiceImpl();
			com.revhire.model.Job job = jobService.getJobById(app.getJobId());
			if (job != null) {
				ns.notifyEmployer(job.getCompanyId(), "Application withdrawn for Job: " + job.getTitle());
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
