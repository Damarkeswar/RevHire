package com.revhire.dao;

import java.util.List;

import com.revhire.model.Job;

public interface JobDao {

	boolean postJob(Job job);

	List<Job> getAllJobs();

	List<Job> searchJobs(
			String title,
			String location,
			Integer minExperience,
			String companyName,
			Double minSalary,
			String jobType);

	List<Job> getJobsByCompanyId(int companyId);

	boolean updateJob(Job job);

	boolean deleteJob(int jobId);

	Job getJobById(int jobId);

}
