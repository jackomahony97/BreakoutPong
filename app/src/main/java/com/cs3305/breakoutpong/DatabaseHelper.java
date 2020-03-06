package com.cs3305.breakoutpong;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to initialize database and implement simple db functions
 * <p>
 * Includes code from http://www.androidtutorialshub.com/android-login-and-register-with-sqlite-database-tutorial/
 * with modifications as per comments on page regarding code reuse
 * and https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase
 * with modifications as per developer.android.com code reuse licence
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private Context context;
    /**
     * int : represents the database version
     * TODO must be incremented by 1 if schema is changed
     */
    public static final int DATABASE_VERSION = 3;
    /**
     * int : represents the name of the database
     */
    public static final String DATABASE_NAME = "Users.db";
    /**
     * String : represents the sql table schema command
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersContract.FeedEntry.TABLE_NAME + " (" +
                    UsersContract.FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    UsersContract.FeedEntry.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                    UsersContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    UsersContract.FeedEntry.COLUMN_NAME_WINTOTAL + " TEXT," +
                    UsersContract.FeedEntry.COLUMN_NAME_HIGHSCORE + " TEXT)";
    /**
     * String : represents the drop sql table schema command
     */
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UsersContract.FeedEntry.TABLE_NAME;


    /**
     * Constructor to call super(SQLiteOpenHelper)
     *
     * @param context represents environment data and it provide access to databases and other things
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * OnCreate is called when class is called
     *
     * @param db SQLiteDatabase : represents a Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Mechanism for upgrading database
     *
     * @param db         SQLiteDatabase : represents a Database
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
     *
     * @param db         db SQLiteDatabase : represents a Database
     * @param oldVersion int : represents the database version
     * @param newVersion int : represents the database version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * Mechanism for creating a new user
     *
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
     * Mechanism for updating high score
     *
     * @param highScore: Users is a class representing a users account
     */
    public boolean addHighScore(int highScore) {
        String highscore = Integer.toString(highScore);

        // array of columns to fetch
        String[] columns = {
                UsersContract.FeedEntry.COLUMN_NAME_HIGHSCORE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = UsersContract.FeedEntry.COLUMN_NAME_EMAIL + " = ?";

        String email = ((GlobalClass) ((Activity) context).getApplication()).getEmail();

        // selection argument
        String[] selectionArgs = {email};


        Cursor cursor = db.query(UsersContract.FeedEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();



        String dbscore;

        if (cursorCount > 0){
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow("highscore");
            dbscore = cursor.getString(index);
            cursor.close();
            db.close();
        } else {
            cursor.close();
            db.close();
            return false;
        }
        if (dbscore == null){
            dbscore = "0";
        }
        int intdbscore = Integer.parseInt(dbscore);

        if(highScore > intdbscore){
            SQLiteDatabase writedb = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("highscore", highscore);

            String[] args = new String[]{email};
            writedb.update(UsersContract.FeedEntry.TABLE_NAME, values, "email=?" , args);

            writedb.close();
            return true;
        }
        return false;
    }


    public boolean addToWinTotal(){
        // array of columns to fetch
        String[] columns = {
                UsersContract.FeedEntry.COLUMN_NAME_WINTOTAL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = UsersContract.FeedEntry.COLUMN_NAME_EMAIL+ " = ?";

        String email = ((GlobalClass) ((Activity) context).getApplication()).getEmail();

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

        String dbwintotal;

        if (cursorCount > 0){
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow("wintotal");
            dbwintotal = cursor.getString(index);
        } else {
            return false;
        }

        cursor.close();
        db.close();

        if (dbwintotal == null){
            dbwintotal = "0";
        }

        int intdbwintotal = Integer.parseInt(dbwintotal);
        intdbwintotal += 1;
        String stringdbwintotal = Integer.toString(intdbwintotal);


        SQLiteDatabase writedb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("wintotal", stringdbwintotal);

        String[] args = new String[]{email};
        writedb.update(UsersContract.FeedEntry.TABLE_NAME, values, "email=?" , args);
        writedb.close();
        return true;
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
                UsersContract.FeedEntry.COLUMN_NAME_EMAIL
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

        return cursorCount > 0;
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
                UsersContract.FeedEntry.COLUMN_NAME_EMAIL
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
