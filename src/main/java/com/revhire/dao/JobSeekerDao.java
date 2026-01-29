package com.revhire.dao;

public interface JobSeekerDao {

	int createJobSeeker(int userId, String fullName);

	int getJobSeekerIdByUserId(int userId);

	java.util.List<com.revhire.model.JobSeeker> searchJobSeekers(String skills, Integer experience, String education);
}
