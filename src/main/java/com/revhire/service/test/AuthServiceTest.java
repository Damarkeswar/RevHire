package com.revhire.service.test;

import com.revhire.model.User;
import com.revhire.service.AuthService;
import com.revhire.service.AuthServiceImpl;

public class AuthServiceTest {

    public static void main(String[] args) {

        AuthService authService = new AuthServiceImpl();

        User user = authService.login("js1@gmail.com", "js123");

        if (user != null) {
            System.out.println("✅ Service Login Test Passed");
        } else {
            System.out.println("❌ Service Login Test Failed");
        }
    }
}
