package com.revhire.service;

import com.revhire.model.User;

public interface AuthService {

    boolean register(User user);

    User login(String email, String password);

    boolean changePassword(int userId, String newPassword);

    User getUserByEmail(String email);

    boolean resetPassword(String email, String newPassword);
}
