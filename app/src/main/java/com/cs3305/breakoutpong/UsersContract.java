package com.cs3305.breakoutpong;

import android.provider.BaseColumns;

public class UsersContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UsersContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String _ID = "";
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
