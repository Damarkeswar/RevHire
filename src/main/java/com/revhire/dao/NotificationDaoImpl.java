package com.revhire.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revhire.config.DBConnection;

public class NotificationDaoImpl implements NotificationDao {

    @Override
    public void createNotification(int jobSeekerId, String message) {

        String sql = "INSERT INTO notifications (job_seeker_id, message) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getNotifications(int jobSeekerId) {

        List<String> list = new ArrayList<>();

        String sql = "SELECT message, created_at FROM notifications WHERE job_seeker_id = ? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("message") + " [" + rs.getTimestamp("created_at") + "]");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markAllAsRead(int jobSeekerId) {

        String sql = "UPDATE notifications SET is_read='Y' WHERE job_seeker_id=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEmployerNotification(int companyId, String message) {
        String sql = "INSERT INTO employer_notifications (company_id, message) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getEmployerNotifications(int companyId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT message, created_at FROM employer_notifications WHERE company_id = ? ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("message") + " [" + rs.getTimestamp("created_at") + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markEmployerAsRead(int companyId) {
        String sql = "UPDATE employer_notifications SET is_read='Y' WHERE company_id=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
