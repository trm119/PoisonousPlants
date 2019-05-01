package com.example.plants.poisonousplants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.plants.poisonousplants.View.User;
import com.example.plants.poisonousplants.View.helpers.DbHelper;
import com.example.plants.poisonousplants.View.User;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;


public class DBHelperTest extends AndroidTestCase {

    private static String mUserName;
    private static String mEmail;
    private static String mPassword;
    private static long mID;


    public void testAddUser_True() {
        DbHelper dbhelper = new DbHelper(mContext);

        mUserName = "user500";
        mEmail = "user500@email.com";
        mPassword = "user500pass";

        User new_user = new User(null, mUserName, mEmail, mPassword);

        mID = dbhelper.addUser(new_user);

        assertTrue(mID != -1);
    }

    public void testAuthenticateUser_True() {

        mUserName = "user600";
        mEmail = "user600@email.com";
        mPassword = "user600pass";

        DbHelper dbhelper = new DbHelper(mContext);

        User new_user = new User(null, mUserName, mEmail, mPassword);

        mID = dbhelper.addUser(new_user);

        User current_user = dbhelper.authenticateUser(new_user);

        assertNotNull(current_user);

    }

    public void testAuthenticateUser_False() {

        mUserName = "u1000";
        mEmail = "u1000@email.com";
        mPassword = "u1000pass";

        DbHelper dbhelper = new DbHelper(mContext);

        User new_user = new User(null, mUserName, mEmail, mPassword);
        User current_user = dbhelper.authenticateUser(new_user);

        assertNull(current_user);

    }


    public void testDoesEmailExist_True() {

        DbHelper dbhelper = new DbHelper(mContext);

        mUserName = "user600";
        mEmail = "user600@email.com";
        mPassword = "user600pass";

        User new_user = new User(null, mUserName, mEmail, mPassword);

        mID = dbhelper.addUser(new_user);
        boolean result = dbhelper.doesEmailExists(mEmail);
        assertTrue(result);

    }

    public void testDoesEmailExist_False() {

        DbHelper dbhelper = new DbHelper(mContext);

        mUserName = "user700";
        mEmail = "email";
        mPassword = "user700pass";

        User new_user = new User(null, mUserName, mEmail, mPassword);

        boolean result = dbhelper.doesEmailExists(mEmail);
        assertFalse(result);

    }

}
