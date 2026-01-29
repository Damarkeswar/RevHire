package com.revhire.model;

import java.sql.Date;

public class Job {

	private int jobId;
	private int companyId;
	private String title;
	private String description;
	private String skills;
	private int minExperience;
	private String location;
	private double salaryMin;
	private double salaryMax;
	private String jobType;
	private Date deadline;
	private String status;
	private String educationRequired;

	// Getters & Setters
	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public int getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(int minExperience) {
		this.minExperience = minExperience;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(double salaryMin) {
		this.salaryMin = salaryMin;
	}

	public double getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(double salaryMax) {
		this.salaryMax = salaryMax;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEducationRequired() {
		return educationRequired;
	}

	public void setEducationRequired(String educationRequired) {
		this.educationRequired = educationRequired;
	}
}
