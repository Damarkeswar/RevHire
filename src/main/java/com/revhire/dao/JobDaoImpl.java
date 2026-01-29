package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.config.DBConnection;
import com.revhire.model.Job;

public class JobDaoImpl implements JobDao {

	@Override
	public boolean postJob(Job job) {

		String sql = "INSERT INTO jobs (company_id, job_title, job_description, skills_required, min_experience, location, salary_min, salary_max, job_type, deadline, status, education_required) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, job.getCompanyId());
			ps.setString(2, job.getTitle());
			ps.setString(3, job.getDescription());
			ps.setString(4, job.getSkills());
			ps.setInt(5, job.getMinExperience());
			ps.setString(6, job.getLocation());
			ps.setDouble(7, job.getSalaryMin());
			ps.setDouble(8, job.getSalaryMax());
			ps.setString(9, job.getJobType());
			ps.setDate(10, job.getDeadline());
			ps.setString(11, "OPEN");
			ps.setString(12, job.getEducationRequired());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Job> getAllJobs() {

		List<Job> jobs = new ArrayList<>();
		String sql = "SELECT * FROM jobs WHERE status='OPEN'";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Job job = new Job();
				job.setJobId(rs.getInt("job_id"));
				job.setTitle(rs.getString("job_title"));
				job.setLocation(rs.getString("location"));
				job.setSkills(rs.getString("skills_required"));
				job.setMinExperience(rs.getInt("min_experience"));
				job.setSalaryMin(rs.getDouble("salary_min"));
				job.setSalaryMax(rs.getDouble("salary_max"));
				job.setEducationRequired(rs.getString("education_required"));

				jobs.add(job);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

	@Override
	public List<Job> searchJobs(
			String title,
			String location,
			Integer minExperience,
			String companyName,
			Double minSalary,
			String jobType) {

		List<Job> jobs = new ArrayList<>();

		StringBuilder sql = new StringBuilder(
				"SELECT j.* FROM jobs j " +
						"JOIN companies c ON j.company_id = c.company_id " +
						"WHERE j.status = 'OPEN' ");

		if (title != null)
			sql.append("AND LOWER(j.job_title) LIKE ? ");
		if (location != null)
			sql.append("AND LOWER(j.location) LIKE ? ");
		if (minExperience != null)
			sql.append("AND j.min_experience <= ? ");
		if (companyName != null)
			sql.append("AND LOWER(c.company_name) LIKE ? ");
		if (minSalary != null)
			sql.append("AND j.salary_max >= ? ");
		if (jobType != null)
			sql.append("AND LOWER(j.job_type) = ? ");

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString())) {

			int index = 1;

			if (title != null)
				ps.setString(index++, "%" + title.toLowerCase() + "%");
			if (location != null)
				ps.setString(index++, "%" + location.toLowerCase() + "%");
			if (minExperience != null)
				ps.setInt(index++, minExperience);
			if (companyName != null)
				ps.setString(index++, "%" + companyName.toLowerCase() + "%");
			if (minSalary != null)
				ps.setDouble(index++, minSalary);
			if (jobType != null)
				ps.setString(index++, jobType.toLowerCase());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Job job = new Job();
				job.setJobId(rs.getInt("job_id"));
				job.setTitle(rs.getString("job_title"));
				job.setLocation(rs.getString("location"));
				job.setMinExperience(rs.getInt("min_experience"));
				job.setSalaryMin(rs.getDouble("salary_min"));
				job.setSalaryMax(rs.getDouble("salary_max"));
				job.setJobType(rs.getString("job_type"));
				job.setEducationRequired(rs.getString("education_required"));
				jobs.add(job);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

	@Override
	public List<Job> getJobsByCompanyId(int companyId) {
		List<Job> jobs = new ArrayList<>();
		String sql = "SELECT * FROM jobs WHERE company_id = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Job job = new Job();
				job.setJobId(rs.getInt("job_id"));
				job.setCompanyId(rs.getInt("company_id"));
				job.setTitle(rs.getString("job_title"));
				job.setDescription(rs.getString("job_description"));
				job.setSkills(rs.getString("skills_required"));
				job.setMinExperience(rs.getInt("min_experience"));
				job.setLocation(rs.getString("location"));
				job.setSalaryMin(rs.getDouble("salary_min"));
				job.setSalaryMax(rs.getDouble("salary_max"));
				job.setJobType(rs.getString("job_type"));
				job.setDeadline(rs.getDate("deadline"));
				job.setStatus(rs.getString("status"));
				job.setEducationRequired(rs.getString("education_required"));
				jobs.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

	@Override
	public boolean updateJob(Job job) {
		String sql = "UPDATE jobs SET job_title=?, job_description=?, skills_required=?, min_experience=?, location=?, salary_min=?, salary_max=?, job_type=?, deadline=?, status=?, education_required=? WHERE job_id=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, job.getTitle());
			ps.setString(2, job.getDescription());
			ps.setString(3, job.getSkills());
			ps.setInt(4, job.getMinExperience());
			ps.setString(5, job.getLocation());
			ps.setDouble(6, job.getSalaryMin());
			ps.setDouble(7, job.getSalaryMax());
			ps.setString(8, job.getJobType());
			ps.setDate(9, job.getDeadline());
			ps.setString(10, job.getStatus());
			ps.setString(11, job.getEducationRequired());
			ps.setInt(12, job.getJobId());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteJob(int jobId) {
		String sql = "DELETE FROM jobs WHERE job_id = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, jobId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Job getJobById(int jobId) {
		String sql = "SELECT * FROM jobs WHERE job_id = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, jobId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Job job = new Job();
				job.setJobId(rs.getInt("job_id"));
				job.setCompanyId(rs.getInt("company_id"));
				job.setTitle(rs.getString("job_title"));
				job.setDescription(rs.getString("job_description"));
				job.setSkills(rs.getString("skills_required"));
				job.setMinExperience(rs.getInt("min_experience"));
				job.setLocation(rs.getString("location"));
				job.setSalaryMin(rs.getDouble("salary_min"));
				job.setSalaryMax(rs.getDouble("salary_max"));
				job.setJobType(rs.getString("job_type"));
				job.setDeadline(rs.getDate("deadline"));
				job.setStatus(rs.getString("status"));
				job.setEducationRequired(rs.getString("education_required"));
				return job;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
