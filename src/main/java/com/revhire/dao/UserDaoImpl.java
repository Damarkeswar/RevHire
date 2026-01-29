package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revhire.config.DBConnection;
import com.revhire.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl implements UserDao {

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public int registerUser(User user) {

		String sql = "INSERT INTO users (email, password_hash, role, is_active, created_at, security_question, security_answer) VALUES (?, ?, ?, 'Y', SYSTIMESTAMP, ?, ?)";

		try (Connection con = DBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = con.prepareStatement(sql, new String[] { "user_id" })) {

				ps.setString(1, user.getEmail());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getRole());
				ps.setString(4, user.getSecurityQuestion());
				ps.setString(5, user.getSecurityAnswer());

				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int userId = rs.getInt(1);

					con.commit();
					logger.info("User {} registered with DB ID: {}", user.getEmail(), userId);
					return userId;
				}
			}

			con.rollback();
			logger.warn("Registration rolled back for user: {}", user.getEmail());

		} catch (Exception e) {
			logger.error("Database error during registration for {}: {}", user.getEmail(), e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setEmail(rs.getString("email"));
				u.setRole(rs.getString("role"));
				u.setSecurityQuestion(rs.getString("security_question"));
				u.setSecurityAnswer(rs.getString("security_answer"));
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean resetPassword(String email, String newPassword) {
		String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newPassword);
			ps.setString(2, email);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User login(String email, String password) {

		String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ? AND is_active = 'Y'";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setEmail(rs.getString("email"));
				u.setRole(rs.getString("role"));
				logger.info("Database login successful for: {}", email);
				return u;
			}
			logger.warn("Database login failed (invalid credentials) for: {}", email);

		} catch (Exception e) {
			logger.error("Database error during login for {}: {}", email, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean changePassword(int userId, String newPassword) {
		String sql = "UPDATE users SET password_hash=? WHERE user_id=?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, newPassword);
			ps.setInt(2, userId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean emailExists(String email) {

		String sql = "SELECT 1 FROM users WHERE email = ?";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			return rs.next(); // true if exists

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
