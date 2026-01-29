package com.revhire.service;

import java.util.List;
import com.revhire.model.Application;

public interface ApplicationService {

	boolean applyForJob(Application application);

	List<Application> viewMyApplications(int jobSeekerId);

	boolean changeStatus(int applicationId, String status, String comment);

	boolean withdraw(int applicationId, String reason);

	List<Application> getApplicationsByJob(int jobId);

	java.util.Map<String, Integer> getStatisticsByJob(int jobId);
}
