package com.example.client.model;
/**
 * The userSession class is a facility of the class user to get the login and out.
 */
public class UserSession {
    private static UserSession instance;
    private String email;
    private String password;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
