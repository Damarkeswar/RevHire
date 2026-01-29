package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revhire.config.DBConnection;

public class EducationDaoImpl implements EducationDao {

    @Override
    public boolean addEducation(int jobSeekerId, String degree,
            String institution, int startYear, int endYear) {

        String sql = "INSERT INTO education (job_seeker_id, degree, institution, start_year, end_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ps.setString(2, degree);
            ps.setString(3, institution);
            ps.setInt(4, startYear);
            ps.setInt(5, endYear);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public java.util.List<com.revhire.model.Education> getEducationByJobSeeker(int jobSeekerId) {
        java.util.List<com.revhire.model.Education> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM education WHERE job_seeker_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jobSeekerId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.revhire.model.Education edu = new com.revhire.model.Education();
                edu.setEducationId(rs.getInt("education_id"));
                edu.setJobSeekerId(rs.getInt("job_seeker_id"));
                edu.setDegree(rs.getString("degree"));
                edu.setInstitution(rs.getString("institution"));
                edu.setStartYear(rs.getInt("start_year"));
                edu.setEndYear(rs.getInt("end_year"));
                list.add(edu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
