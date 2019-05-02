package com.example.plants.poisonousplants.View.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.plants.poisonousplants.View.User;

/**
 * The class represents a DbHelper object
 *
 * @author  Rogelio Paniagua
 * @version 1.0
 * @since   2019-03-13
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "poisonous_plants";
    private static final String USERS_TABLE  = "users";
    private static final int DB_VERSION = 1;
    private static final String ID = "id";
    private static final String USER_NAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD =  "password";
    private static final String SQL_USERS_TABLE = " CREATE TABLE " + USERS_TABLE
            + " ( "
            + ID + " INTEGER PRIMARY KEY, "
            + USER_NAME + " TEXT, "
            + EMAIL + " TEXT, "
            + PASSWORD + " TEXT"
            + " ) ";



    /** Creates a DbHelper with the specified Context
     * @param context The specific Context
     */
    public DbHelper (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /** Creates the USERS Table
     * @param sqLite_DB SQLiteDatabase object used to create the USER Table
     */
    @Override
    public void onCreate(SQLiteDatabase sqLite_DB){
        sqLite_DB.execSQL(SQL_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLite_DB, int i, int i1){
        sqLite_DB.execSQL(" DROP TABLE IF EXISTS " + USERS_TABLE);
    }

    /** Adds a User to the USER Table in the DB
     * @param user User object to be added to the table
     * @return todo_ID A long representing the ID value
     *  indicating whether the user has been added.
     */
    public long addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUserName());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());

        long todo_ID = db.insert(USERS_TABLE, null, values);
        return todo_ID;

    }

    /** Checks whether a user exists in the DB
     * @param user User object that is checked against the table.
     * @return user1 A User that is correctly found in the DB
     *  or null otherwise.
     */
    public User authenticateUser(User user) {

    SQLiteDatabase db = this.getReadableDatabase();
        Cursor curr = db.query(USERS_TABLE,
                new String[]{ID, USER_NAME, EMAIL, PASSWORD},
                EMAIL + "=?",
                new String[]{user.getEmail()},
                null, null, null);

        if (curr != null && curr.moveToFirst() && curr.getCount() > 0) {

            User user1 = new User(curr.getString(0), curr.getString(1), curr.getString(2), curr.getString(3));

            if(user.getPassword().equalsIgnoreCase(user1.getPassword()))
                return user1;
        }

        curr.close();
        return null;
    }

    /** Checks whether a user ex
     * @param user User object that is checked against the table.
     * @return user1 A User that is correctly found in the DB
     *  or null otherwise.
     */
    public boolean doesEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curr = db.query(USERS_TABLE,
                new String[]{ID,USER_NAME, EMAIL, PASSWORD},
                EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if(curr != null && curr.moveToFirst() && curr.getCount() > 0) {
            return true;
        }

        curr.close();
        return false;
    }

}

