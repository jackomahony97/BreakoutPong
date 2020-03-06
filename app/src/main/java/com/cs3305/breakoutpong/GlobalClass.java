package com.cs3305.breakoutpong;

import android.app.Application;


/**
 * Holds email (primary key) after login to enable score and win total updates in database
 */
public class GlobalClass extends Application {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String aEmail) {
        email = aEmail;
    }
}