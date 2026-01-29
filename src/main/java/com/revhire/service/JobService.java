package com.revhire.service;

import java.util.List;
import com.revhire.model.Job;

public interface JobService {

	boolean postJob(Job job);

	List<Job> viewAllJobs();

	List<Job> searchJobs(
			String title,
			String location,
			Integer minExperience,
			String companyName,
			Double minSalary,
			String jobType);

	List<Job> viewJobsByCompany(int companyId);

	boolean updateJob(Job job);

	boolean deleteJob(int jobId);

	Job getJobById(int jobId);

	List<Job> getMatchingJobs(int jobSeekerId);
}
