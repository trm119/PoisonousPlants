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


/**
 * The class is used as a Test class for a DbHelper
 *
 * @author  Rogelio Paniagua
 * @version 1.0
 * @since   2019-04-06
 */
public class DBHelperTest extends AndroidTestCase {

    private static String mUserName;
    private static String mEmail;
    private static String mPassword;
    private static long mID;


    /** Tests for successfully adding a user to the DB
     *
     */
    public void testAddUser_True() {
        DbHelper dbhelper = new DbHelper(mContext);

        mUserName = "user500";
        mEmail = "user500@email.com";
        mPassword = "user500pass";

        User new_user = new User(null, mUserName, mEmail, mPassword);

        mID = dbhelper.addUser(new_user);

        assertTrue(mID != -1);
    }

    /** Tests for successfully authenticating a user in the DB
     *
     */
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

    /** Tests for unsuccessfully authenticating a
     *  user in the DB
     *
     */
    public void testAuthenticateUser_False() {

        mUserName = "u1000";
        mEmail = "u1000@email.com";
        mPassword = "u1000pass";

        DbHelper dbhelper = new DbHelper(mContext);

        User new_user = new User(null, mUserName, mEmail, mPassword);
        User current_user = dbhelper.authenticateUser(new_user);

        assertNull(current_user);

    }

    /** Tests for successfully checking if a certain
     *  email exists in the DB
     *
     */
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

    /** Tests for successfully checking if a certain
     *  email does not exist in the DB
     *
     */
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
