package com.example.android.health_in_time;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertNotNull;

/**
 * Created by samir692 on 1/8/18.
 */
@RunWith(AndroidJUnit4.class)
public class HearRateMonitorInstrumentedTest {

    @Rule
    public ActivityTestRule<HeartRateMonitor> mActivityRule =
            new ActivityTestRule<>(HeartRateMonitor.class);

    private HeartRateMonitor mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testLaunch(){
        assertNotNull(mActivity.findViewById(R.id.layout));
    }

/*
    @Test
    public void clickOnYourNavigationItem_ShowsYourScreen() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.your_navigation_menu_item));

        // Check that you Activity was opened.
        String expectedNoStatisticsText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.no_item_available);
        onView(withId(R.id.no_statistics)).check(matches(withText(expectedNoStatisticsText)));
    }
*/
    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    /*
    @Test
    public void testNavigationDrawerAboutMenu() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open()); //open drawer
        onView(withText("About")).perform(click());
        onView(withId(R.id.aboutsptemail)).check(matches(withText(R.string.screen_about_support_email)));
        onView(withId(R.id.aboutcpright)).check(matches(isDisplayed()));
        onView(withId(R.id.aboutprivacy)).check(matches(isDisplayed()));
        onView(withId(R.id.abouttermsconditions)).check(matches(isDisplayed()));
        onView(withId(R.id.aboutsptemail)).perform(click());
    }
    */
}

