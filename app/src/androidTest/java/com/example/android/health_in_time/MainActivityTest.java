package com.example.android.health_in_time;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;

/**
 * Created by samir692 on 1/8/18.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().
                                              addMonitor(LoginActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchMainAndLogin(){
        assertNotNull(mActivity.findViewById(R.id.textview));
        onView(withId(R.id.login_page)).perform(click());
        Activity loginActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 500);
        assertNotNull(loginActivity);
        loginActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}