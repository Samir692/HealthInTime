package com.example.android.health_in_time;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;

import com.example.android.health_in_time.database_sql.DatabaseHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.SecureRandom;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by samir692 on 1/8/18.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseHandlerInstrumentedTest{


    private DatabaseHandler mDataSource;


    @Before
    public void setUp(){

        mDataSource = new DatabaseHandler(InstrumentationRegistry.getTargetContext());
        mDataSource.dropAndCreate();
    }

    @After
    public void finish() {
        mDataSource.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void testDeleteUser(){

        String name = "Firstname";
        String username = "Nickname";
        String emailAddress = "firstname.lastname@g.com";
        String password = "password";
        String salt = "password";


        Contacts user = new Contacts();
        user.set_username(name);
        user.set_nick_name(username);
        user.set_email(emailAddress);

        mDataSource.addUser(user);
        List<Contacts> allusers = mDataSource.getAllUser();
        assertThat(allusers.size(), greaterThan(0));

        mDataSource.deleteUser(user);
        allusers = mDataSource.getAllUser();
        assertThat(allusers.size(), is(0));

    }


    @Test
    public void testAddUser(){
        String name = "Firstname";
        String username = "Nickname";
        String emailAddress = "firstname.lastname@g.com";


        Contacts user = new Contacts();
        user.set_username(name);
        user.set_nick_name(username);
        user.set_email(emailAddress);

        mDataSource.addUser(user);
        List<Contacts> allusers = mDataSource.getAllUser();
        assertThat(allusers.size(), is(1));
    }

    @Test
    public void testAddRate(){

        int id = 4;
        int rate = 25;

        UserRate user_rate = new UserRate();
        user_rate.set_id(id);
        user_rate.set_hear_rate(rate);

        Contacts user = new Contacts();
        user.set_id(id);

        mDataSource.addRate(user_rate);

        List<UserRate> allRates = mDataSource.getAllRate(user);
        assertThat(allRates.size(), is(1));
    }



    @Test
    public void testCheckUser(){

        final SecureRandom rand = new SecureRandom();
        String name = "Firstname";
        String username = "Nickname";
        String emailAddress = "firstname.lastname@g.com";
        byte[] password = new byte[4];
        rand.nextBytes(password);
        byte[] salt = new byte[4];
        rand.nextBytes(salt);

        Contacts user = new Contacts();
        user.set_username(name);
        user.set_nick_name(username);
        user.set_email(emailAddress);
        user.set_password(password);
        user.set_salt(salt);

        mDataSource.addUser(user);
        Contacts user_get_id = mDataSource.getUser(username);
        Contacts user_res = mDataSource.getPassAndSalt(username);

        assertEquals(mDataSource.checkUser(emailAddress, username, 1), true);
        assertEquals(user_res.get_password().length, 4);
        assertThat(user_get_id.get_id(), greaterThan(0));

    }




}
