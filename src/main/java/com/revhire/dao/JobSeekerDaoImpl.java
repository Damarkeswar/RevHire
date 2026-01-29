package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revhire.config.DBConnection;

public class JobSeekerDaoImpl implements JobSeekerDao {

    @Override
    public int createJobSeeker(int userId, String fullName) {

        String sql = "INSERT INTO job_seekers (user_id, full_name) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, new String[] { "job_seeker_id" })) {

            ps.setInt(1, userId);
            ps.setString(2, fullName);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getJobSeekerIdByUserId(int userId) {

        String sql = "SELECT job_seeker_id FROM job_seekers WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("job_seeker_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // not found
    }

    @Override
    public java.util.List<com.revhire.model.JobSeeker> searchJobSeekers(String skills, Integer experience,
            String education) {
        java.util.List<com.revhire.model.JobSeeker> list = new java.util.ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT js.* FROM job_seekers js ");
        if (skills != null)
            sql.append("JOIN skills s ON js.job_seeker_id = s.job_seeker_id ");
        if (experience != null)
            sql.append("JOIN experience e ON js.job_seeker_id = e.job_seeker_id ");
        if (education != null)
            sql.append("JOIN education edu ON js.job_seeker_id = edu.job_seeker_id ");
        sql.append("WHERE 1=1 ");
        if (skills != null)
            sql.append("AND LOWER(s.skill_name) LIKE ? ");
        // Simplified experience check for now
        if (education != null)
            sql.append("AND LOWER(edu.degree) LIKE ? ");

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (skills != null)
                ps.setString(idx++, "%" + skills.toLowerCase() + "%");
            if (education != null)
                ps.setString(idx++, "%" + education.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.revhire.model.JobSeeker js = new com.revhire.model.JobSeeker();
                js.setJobSeekerId(rs.getInt("job_seeker_id"));
                js.setFullName(rs.getString("full_name"));
                list.add(js);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
