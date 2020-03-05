package com.cs3305.breakoutpong;

import android.provider.BaseColumns;


/**
 * The UsersContract class a wrapper for tables for the users database
 *
 * Includes code from https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase
 * with modifications as per developer.android.com code reuse licence
 */
public class UsersContract {

    /**
     * Necessary constructor
     */
    private UsersContract() {}
    /**
     * The FeedEntry class is an Inner class that defines the table contents
     */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_NAME = "name";
        // email is primary key
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
