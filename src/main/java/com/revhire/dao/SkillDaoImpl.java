package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revhire.config.DBConnection;

public class SkillDaoImpl implements SkillDao {

    @Override
    public boolean addSkill(int jobSeekerId,
            String skillName,
            String proficiency) {

        String sql = "INSERT INTO skills (job_seeker_id, skill_name, proficiency_level) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ps.setString(2, skillName);
            ps.setString(3, proficiency);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public java.util.List<com.revhire.model.Skill> getSkillsByJobSeeker(int jobSeekerId) {
        java.util.List<com.revhire.model.Skill> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM skills WHERE job_seeker_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jobSeekerId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                com.revhire.model.Skill skill = new com.revhire.model.Skill();
                skill.setSkillId(rs.getInt("skill_id"));
                skill.setJobSeekerId(rs.getInt("job_seeker_id"));
                skill.setSkillName(rs.getString("skill_name"));
                skill.setProficiency(rs.getString("proficiency_level"));
                list.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
