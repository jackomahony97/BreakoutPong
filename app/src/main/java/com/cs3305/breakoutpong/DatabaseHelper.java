package com.cs3305.breakoutpong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * int : represents the database version
     * TODO must be incremented by 1 if schema is changed
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * int : represents the name of the database
     */
    public static final String DATABASE_NAME = "Users.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersContract.FeedEntry.TABLE_NAME + " (" +
                    UsersContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    UsersContract.FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    UsersContract.FeedEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UsersContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsersContract.FeedEntry.TABLE_NAME;


    /**
     * Constructor : calls super(SQLiteOpenHelper)
     */

    // Context represents environment data and it provide access to databases and other things

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * OnCreate is called when class is called
     * @param db SQLiteDatabase : represents a Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Mechanism for upgrading database
     * @param db SQLiteDatabase : represents a Database
     * @param oldVersion int : represents the database version
     * @param newVersion int : int : represents the database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Mechanism for downgrading database
     * @param db db SQLiteDatabase : represents a Database
     * @param oldVersion int : represents the database version
     * @param newVersion int : represents the database version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * Mechanism for creating a new user
     * @param user : Users is a class representing a users account
     */
    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersContract.FeedEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UsersContract.FeedEntry.COLUMN_NAME_EMAIL, user.getEmail());
        values.put(UsersContract.FeedEntry.COLUMN_NAME_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(UsersContract.FeedEntry.TABLE_NAME, null, values);
        db.close();
    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Users> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                UsersContract.FeedEntry._ID,
                UsersContract.FeedEntry.COLUMN_NAME_NAME,
                UsersContract.FeedEntry.COLUMN_NAME_EMAIL,
                UsersContract.FeedEntry.COLUMN_NAME_PASSWORD
        };
        // sorting orders
        String sortOrder =
                UsersContract.FeedEntry.COLUMN_NAME_NAME + " ASC";
        List<Users> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(UsersContract.FeedEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(UsersContract.FeedEntry._ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(UsersContract.FeedEntry.COLUMN_NAME_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UsersContract.FeedEntry.COLUMN_NAME_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UsersContract.FeedEntry.COLUMN_NAME_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersContract.FeedEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UsersContract.FeedEntry.COLUMN_NAME_EMAIL, user.getEmail());
        values.put(UsersContract.FeedEntry.COLUMN_NAME_PASSWORD, user.getPassword());

        // updating row
        db.update(UsersContract.FeedEntry.TABLE_NAME, values, UsersContract.FeedEntry._ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(UsersContract.FeedEntry.TABLE_NAME, UsersContract.FeedEntry._ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                UsersContract.FeedEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = UsersContract.FeedEntry.COLUMN_NAME_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(UsersContract.FeedEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                UsersContract.FeedEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = UsersContract.FeedEntry.COLUMN_NAME_EMAIL + " = ?" + " AND " + UsersContract.FeedEntry.COLUMN_NAME_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(UsersContract.FeedEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
