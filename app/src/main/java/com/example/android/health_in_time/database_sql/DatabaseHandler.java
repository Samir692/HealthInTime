package com.example.android.health_in_time.database_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.android.health_in_time.Contacts;
import com.example.android.health_in_time.UserRate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir692 on 11/30/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "usersManager";


    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts table Rate name
    private static final String TABLE_RATES = "rates";



    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_NICK_NAME = "nick_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_SALT = "user_salt";

    // User Table Rate Columns names
    private static final String COLUMN_USER_RATE_ID = "user_rate_id";
    private static final String COLUMN_USER_RATE = "user_rate";
    private static final String COLUMN_USER_RATE_DATE = "user_rate_date";



    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_NICK_NAME + " TEXT NOT NULL UNIQUE,"
            + COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE," + COLUMN_USER_PASSWORD + " BLOB,"
            + COLUMN_USER_SALT + " BLOB" + ")";

    // create table sql query for rates
    private String CREATE_USER_RATE_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_RATES + "("
            + COLUMN_USER_RATE_ID + " INTEGER,"
            + COLUMN_USER_RATE + " INTEGER," + COLUMN_USER_RATE_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;

    // drop table sql query for rate
    private String DROP_USER_RATE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RATES;

    //constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void deleteDb(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_RATE_TABLE);
    }


    public void dropAndCreate() {
        //Drop User Table if exist
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_USER_RATE_TABLE);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_USER_RATE_TABLE);

        // Create tables again
        onCreate(db);

    }

    public void createTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_RATE_TABLE);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(Contacts user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.get_username());
        values.put(COLUMN_USER_NICK_NAME, user.get_nick_name());
        values.put(COLUMN_USER_EMAIL, user.get_email());
        values.put(COLUMN_USER_PASSWORD, user.get_password());
        values.put(COLUMN_USER_SALT, user.get_salt());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    /**
     * This method is to create user rate
     *
     * @param rate
     */
    public void addRate(UserRate rate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //System.out.println("USERIDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD= " + Integer.toString(rate.get_id()));
        values.put(COLUMN_USER_RATE_ID, rate.get_id());
        values.put(COLUMN_USER_RATE, rate.get_hear_rate());
        // Inserting Row
        db.insert(TABLE_RATES, null, values);
        db.close();
    }



    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(Contacts user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_CONTACTS, COLUMN_USER_NICK_NAME + " = ?",
                new String[]{user.get_nick_name()});
        db.close();
    }

    /**
     * This method is to delete user rate record
     *
     * @param user_rate
     */
    public void deleteUserRate(UserRate user_rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_RATES, COLUMN_USER_RATE_ID + " = ?",
                new String[]{String.valueOf(user_rate.get_id())});
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Contacts> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_NICK_NAME,
                COLUMN_USER_PASSWORD
        };


        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<Contacts> userList = new ArrayList<Contacts>();

        SQLiteDatabase db = this.getReadableDatabase();


        // query the user table
        /**
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CONTACTS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contacts user = new Contacts();
                //user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.set_username(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.set_nick_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NICK_NAME)));
                user.set_email(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.set_password(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
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
     * This method is to fetch user and return the list of user rates
     *
     * @return list
     */
    public List<UserRate> getAllRate(Contacts user) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT user_rate, user_rate_date FROM rates WHERE user_rate_id =" + user.get_id() + " ORDER BY user_rate_date" ;
        Cursor cursor = db.rawQuery(query,null);


        List<UserRate> userRateList = new ArrayList<UserRate>();

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserRate rate = new UserRate();

                //user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                rate.set_hear_rate((cursor.getInt(cursor.getColumnIndex(COLUMN_USER_RATE))));
                rate.set_data_time((cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_RATE_DATE))));
                // Adding user record to list
                userRateList.add(rate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userRateList;
    }


    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(Contacts user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.get_username());
        values.put(COLUMN_USER_NICK_NAME, user.get_nick_name());
        values.put(COLUMN_USER_EMAIL, user.get_email());
        values.put(COLUMN_USER_PASSWORD, user.get_password());

        // updating row
        db.update(TABLE_CONTACTS, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.get_id())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param nick_name
     * @return true/false
     */
    public boolean checkUser(String email, String nick_name, int wh) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " OR " + COLUMN_USER_NICK_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {email, nick_name};

        // query user table with condition
        /**
         * SELECT user_id FROM user WHERE user_email = 'user@inf.elte.hu';
         */
        Cursor cursor = db.query(TABLE_CONTACTS, //Table to query
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
     * @param nickname
     * @return Contacts
     */
    public Contacts getPassAndSalt(String nickname) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_SALT
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NICK_NAME + " = ?";

        // selection arguments
        String[] selectionArgs = {nickname};

        // query user table with conditions
        /**
         * SELECT user_id FROM user WHERE user_nickname = 'user692';
         */
        Cursor cursor = db.query(TABLE_CONTACTS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        //System.out.println("LENGTH OF CURSOSRRRRRRRRRRRR = " +  cursorCount);

        Contacts user = new Contacts();

        if (cursorCount == 1) {
            if (cursor.moveToFirst()) {
                do {
                    //System.out.println("COULD GET USERRRRRRRRRRRRRRRRRRRR");
                    user.set_id((Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)))));
                    user.set_password((cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_PASSWORD))));
                    user.set_salt((cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_SALT))));
                    //USER.set_hear_rate((cursor.getInt(cursor.getColumnIndex(COLUMN_USER_RATE))));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return user;
        }

        return user;

    }




    /**
     * This method fetches user and returns user records
     *
     * @param nickname
     * @return user
     */
    public Contacts getUser(String nickname) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        if (nickname == null){
           // System.out.println("USERNAMEEEEEEEEEEEEEEEEEEEEE" + nickname);
            return new Contacts();
        }
        String selection = COLUMN_USER_NICK_NAME + " = ?" ;

        // selection arguments
        String[] selectionArgs = {nickname};

        // query user table with conditions
        /**
         * SELECT user_id FROM user WHERE user_nickname = 'user692';
         */
        Cursor cursor = db.query(TABLE_CONTACTS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        Contacts user = new Contacts();

        if (cursorCount == 1) {
            if (cursor.moveToFirst()) {
                do {

                    user.set_id((Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)))));
                    //USER.set_hear_rate((cursor.getInt(cursor.getColumnIndex(COLUMN_USER_RATE))));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return user;
        }

        System.out.print("Duplicated Users");
        return user;
    }


}
