package com.revhire.dao;

import com.revhire.model.User;

public interface UserDao {

    int registerUser(User user);

    User login(String email, String password);

    boolean changePassword(int userId, String newPassword);

    boolean emailExists(String email);

    User getUserByEmail(String email);

    boolean resetPassword(String email, String newPassword);
}
