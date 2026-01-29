package com.revhire.dao;

import java.sql.*;

import com.revhire.config.DBConnection;
import com.revhire.model.Company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyDaoImpl implements CompanyDao {

    private static final Logger logger = LogManager.getLogger(CompanyDaoImpl.class);

    @Override
    public int createCompany(Company c) {

        String sql = "INSERT INTO companies (employer_id, company_name, industry, company_size, description, website, location, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, new String[] { "company_id" })) {

            ps.setInt(1, c.getEmployerId());
            ps.setString(2, c.getCompanyName());
            ps.setString(3, c.getIndustry());
            ps.setInt(4, c.getCompanySize());
            ps.setString(5, c.getDescription());
            ps.setString(6, c.getWebsite());
            ps.setString(7, c.getLocation());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                logger.info("Company created in DB with ID: {}", id);
                return id;
            }

        } catch (Exception e) {
            logger.error("Error creating company in DB: {}", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Company getCompanyByEmployerId(int employerId) {

        String sql = "SELECT * FROM companies WHERE employer_id = ?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Company c = new Company();
                c.setCompanyId(rs.getInt("company_id"));
                c.setEmployerId(rs.getInt("employer_id"));
                c.setCompanyName(rs.getString("company_name"));
                c.setIndustry(rs.getString("industry"));
                c.setCompanySize(rs.getInt("company_size"));
                c.setDescription(rs.getString("description"));
                c.setWebsite(rs.getString("website"));
                c.setLocation(rs.getString("location"));
                c.setCreatedAt(rs.getTimestamp("created_at"));
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCompany(Company c) {

        String sql = "UPDATE companies SET company_name=?, industry=?, company_size=?,description=?, website=?, location=? WHERE company_id=?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCompanyName());
            ps.setString(2, c.getIndustry());
            ps.setInt(3, c.getCompanySize());
            ps.setString(4, c.getDescription());
            ps.setString(5, c.getWebsite());
            ps.setString(6, c.getLocation());
            ps.setInt(7, c.getCompanyId());

            boolean updated = ps.executeUpdate() > 0;
            if (updated) {
                logger.info("Company profile updated for Company ID: {}", c.getCompanyId());
            }
            return updated;

        } catch (Exception e) {
            logger.error("Error updating company profile for ID {}: {}", c.getCompanyId(), e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getCompanyIdByUserId(int userId) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Company getCompanyById(int companyId) {
        String sql = "SELECT * FROM companies WHERE company_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Company c = new Company();
                c.setCompanyId(rs.getInt("company_id"));
                c.setEmployerId(rs.getInt("employer_id"));
                c.setCompanyName(rs.getString("company_name"));
                c.setIndustry(rs.getString("industry"));
                c.setCompanySize(rs.getInt("company_size"));
                c.setDescription(rs.getString("description"));
                c.setWebsite(rs.getString("website"));
                c.setLocation(rs.getString("location"));
                c.setCreatedAt(rs.getTimestamp("created_at"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
