package com.example.android.health_in_time;
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by samir692 on 1/8/18.
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity mActivity = null;

    Instrumentation.ActivityMonitor monitorRegister = InstrumentationRegistry.getInstrumentation().
            addMonitor(RegisterActivity.class.getName(), null, false);

    Instrumentation.ActivityMonitor monitorHeartRtMn = InstrumentationRegistry.getInstrumentation().
            addMonitor(HeartRateMonitor.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testLaunchLogin(){
        assertNotNull(mActivity.findViewById(R.id.nestedScrollView));
    }

    @Test
    public void login_process() {
        // Type text and then press the button.
        onView(withId(R.id.textInputEditTextNickName))
                .perform(typeText("Nickname"), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextNickName)).check(matches(withText("Nickname")));
        onView(withId(R.id.textInputEditTextPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextPassword)).check(matches(withText("password")));


        onView(withId(R.id.appCompatButtonLogin)).perform(click());



        String successString = InstrumentationRegistry.getTargetContext().getString(R.string.error_password_match);
/*
        if (successString == null){
            Activity HrtRateActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitorHeartRtMn, 500);
            assertNotNull(HrtRateActivity);
            HrtRateActivity.finish();
        }
*/
    }


    @Test
    public void successfull_login(){
        onView(withId(R.id.textViewLinkRegister)).perform(click());
        Activity RegisterActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitorRegister, 500);
        assertNotNull(RegisterActivity);

        String name = "Firstname";
        String username = "mam";
        String emailAddress = "firstnamelastname@g.com";
        String password = "mam";
        String confirm_password = "mam";

        //find the firstname edit text and type in the first name
        onView(withId(R.id.textInputEditTextName)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextName)).check(matches(withText(name)));

        //find the lastname edit text and type in the last name
        onView(withId(R.id.textInputEditTextNickName)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextNickName)).check(matches(withText(username)));

        //find the email address edit text and type in the email address
        onView(withId(R.id.textInputEditTextEmail)).perform(typeText(emailAddress), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextEmail)).check(matches(withText(emailAddress)));

        //find the password edit text and type in the password
        onView(withId(R.id.textInputEditTextPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextPassword)).check(matches(withText(password)));

        //find the password edit text and type in the password
        onView(withId(R.id.textInputEditTextConfirmPassword)).perform(typeText(confirm_password), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmPassword)).check(matches(withText(confirm_password)));

        //click the signup button
        onView(withId(R.id.nestedScrollView)).perform(swipeUp()).perform(click());
        onView(withId(R.id.appCompatButtonRegister)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.appCompatButtonRegister)).perform(swipeUp(), click());

        //check that we can see the success screen with success message
        String successString = InstrumentationRegistry.getTargetContext().getString(R.string.success_message | R.string.error_email_nickname_exists);

        RegisterActivity.finish();

        onView(withId(R.id.textInputEditTextNickName))
                .perform(typeText("mam"), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextNickName)).check(matches(withText("mam")));

        onView(withId(R.id.textInputEditTextPassword))
                .perform(typeText("mam"), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextPassword)).check(matches(withText("mam")));


        onView(withId(R.id.appCompatButtonLogin)).perform(click());




        //String successString2 = InstrumentationRegistry.getTargetContext().getString(R.string.error_password_match);

        //if (successString2 == null){
            Activity HrtRateActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitorHeartRtMn, 500);
            assertNotNull(HrtRateActivity);
            HrtRateActivity.finish();
        //}

    }

    @Test
    public void signup_process(){
        onView(withId(R.id.textViewLinkRegister)).perform(click());
        Activity RegisterActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitorRegister, 500);
        assertNotNull(RegisterActivity);

        String name = "Firstname";
        String username = "Nickname";
        String emailAddress = "firstnamelastname@g.com";
        String password = "password";
        String confirm_password = "password";

        //find the firstname edit text and type in the first name
        onView(withId(R.id.textInputEditTextName)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextName)).check(matches(withText(name)));

        //find the lastname edit text and type in the last name
        onView(withId(R.id.textInputEditTextNickName)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextNickName)).check(matches(withText(username)));

        //find the email address edit text and type in the email address
        onView(withId(R.id.textInputEditTextEmail)).perform(typeText(emailAddress), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextEmail)).check(matches(withText(emailAddress)));

        //find the password edit text and type in the password
        onView(withId(R.id.textInputEditTextPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextPassword)).check(matches(withText(password)));

        //find the password edit text and type in the password
        onView(withId(R.id.textInputEditTextConfirmPassword)).perform(typeText(confirm_password), closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmPassword)).check(matches(withText(confirm_password)));

        //click the signup button
        onView(withId(R.id.nestedScrollView)).perform(swipeUp()).perform(click());
        onView(withId(R.id.appCompatButtonRegister)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.appCompatButtonRegister)).perform(swipeUp(), click());

        //check that we can see the success screen with success message
        String successString = InstrumentationRegistry.getTargetContext().getString(R.string.success_message | R.string.error_email_nickname_exists);

        RegisterActivity.finish();
    }


    @Test
    public void testAuthenticate(){
        Authenication ath = new Authenication();
        String name = "Firstname";
        String username = "Nickname";
        String emailAddress = "firstname.lastname@g.com";
        char[] password = "password".toCharArray();

        try {
            Contacts user = ath.register(name, username, emailAddress, password);
            assertEquals(name, user.get_username());
            assertEquals(username, user.get_nick_name());
            assertEquals(emailAddress, user.get_email());
            assertNotNull(user.get_password());
            assertNotNull(user.get_salt());

        } catch (Exception e) {
            //e.printStackTrace();
        }


    }



    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }







}
