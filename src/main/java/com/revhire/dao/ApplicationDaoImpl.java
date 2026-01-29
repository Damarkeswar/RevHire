package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.config.DBConnection;
import com.revhire.model.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationDaoImpl implements ApplicationDao {

	private static final Logger logger = LogManager.getLogger(ApplicationDaoImpl.class);

	@Override
	public boolean applyJob(Application application) {

		String sql = "INSERT INTO applications (job_id, job_seeker_id, resume_id, status) VALUES (?,?,?,?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, application.getJobId());
			ps.setInt(2, application.getJobSeekerId());
			ps.setInt(3, application.getResumeId());
			ps.setString(4, "APPLIED");

			boolean success = ps.executeUpdate() > 0;
			if (success) {
				logger.info("Application created in DB for Job ID: {} by Seeker ID: {}", application.getJobId(),
						application.getJobSeekerId());
			}
			return success;

		} catch (Exception e) {
			logger.error("Error creating application in DB: {}", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getJobSeekerIdByApplicationId(int applicationId) {

		String sql = "SELECT job_seeker_id FROM applications WHERE application_id = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, applicationId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("job_seeker_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Application> getApplicationsByJobSeeker(int jobSeekerId) {

		List<Application> list = new ArrayList<>();
		String sql = "SELECT * FROM applications WHERE job_seeker_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, jobSeekerId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Application app = new Application();
				app.setApplicationId(rs.getInt("application_id"));
				app.setJobId(rs.getInt("job_id"));
				app.setStatus(rs.getString("status"));
				app.setAppliedDate(rs.getDate("applied_date"));

				list.add(app);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateApplicationStatus(int applicationId, String status, String comment) {

		String sql = "UPDATE applications SET status=?, employer_comment=? WHERE application_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, status);
			ps.setString(2, comment);
			ps.setInt(3, applicationId);

			boolean success = ps.executeUpdate() > 0;
			if (success) {
				logger.info("Application ID {} status updated to {} in DB", applicationId, status);
			}
			return success;

		} catch (Exception e) {
			logger.error("Error updating application status in DB for ID {}: {}", applicationId, e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean withdrawApplication(int applicationId, String reason) {

		String sql = "UPDATE applications SET status = 'WITHDRAWN', employer_comment = ? WHERE application_id = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, reason);
			ps.setInt(2, applicationId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Application> getApplicationsByJob(int jobId) {
		List<Application> list = new ArrayList<>();
		String sql = "SELECT * FROM applications WHERE job_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, jobId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Application app = new Application();
				app.setApplicationId(rs.getInt("application_id"));
				app.setJobId(rs.getInt("job_id"));
				app.setJobSeekerId(rs.getInt("job_seeker_id"));
				app.setResumeId(rs.getInt("resume_id"));
				app.setStatus(rs.getString("status"));
				app.setAppliedDate(rs.getDate("applied_date"));
				app.setEmployerComment(rs.getString("employer_comment"));
				list.add(app);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public java.util.Map<String, Integer> getStatisticsByJob(int jobId) {
		java.util.Map<String, Integer> stats = new java.util.HashMap<>();
		String sql = "SELECT status, COUNT(*) as count FROM applications WHERE job_id = ? GROUP BY status";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, jobId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				stats.put(rs.getString("status"), rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stats;
	}

	@Override
	public Application getApplicationById(int applicationId) {
		String sql = "SELECT * FROM applications WHERE application_id = ?";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, applicationId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Application app = new Application();
				app.setApplicationId(rs.getInt("application_id"));
				app.setJobId(rs.getInt("job_id"));
				app.setJobSeekerId(rs.getInt("job_seeker_id"));
				app.setResumeId(rs.getInt("resume_id"));
				app.setStatus(rs.getString("status"));
				app.setAppliedDate(rs.getDate("applied_date"));
				app.setEmployerComment(rs.getString("employer_comment"));
				return app;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
