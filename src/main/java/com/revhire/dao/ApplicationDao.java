package com.revhire.dao;

import java.util.List;
import com.revhire.model.Application;

public interface ApplicationDao {

	boolean applyJob(Application application);

	List<Application> getApplicationsByJobSeeker(int jobSeekerId);

	boolean updateApplicationStatus(int applicationId, String status, String comment);

	boolean withdrawApplication(int applicationId, String reason);

	int getJobSeekerIdByApplicationId(int applicationId);

	List<Application> getApplicationsByJob(int jobId);

	java.util.Map<String, Integer> getStatisticsByJob(int jobId);

	Application getApplicationById(int applicationId);
}
