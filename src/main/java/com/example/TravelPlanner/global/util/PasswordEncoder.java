package com.example.TravelPlanner.global.util;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matches(String plainPassword, String encodedPassword) {
        return BCrypt.checkpw(plainPassword, encodedPassword);
    }
}
