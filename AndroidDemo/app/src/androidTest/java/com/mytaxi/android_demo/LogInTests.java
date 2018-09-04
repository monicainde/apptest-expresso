package com.mytaxi.android_demo;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogInTests {
    public String USERNAME = "crazydog335";
    public String PASSWORD = "venture";
    UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public void allowCurrentPermission() throws UiObjectNotFoundException {
        UiObject allowButton = mDevice.findObject(new UiSelector().text("ALLOW"));
        //new UiSelector().
        if(allowButton.exists())
            allowButton.click();
    }

    @Before
    public void beforeEachTest(){
        try {
            allowCurrentPermission();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void logIn() {
        // Type username/password and then press the log in button.
        onView(withId(R.id.edt_username))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check that landing page is loaded
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    //@Test
    public void searchDriver() {
        // Type text and then press the search button.
        onView(withId(R.id.textSearch))
                .perform(typeText("sa"), closeSoftKeyboard());

        //onData(equalTo("Sarah Scott")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        //Click on Call Button
        onView(withId(R.id.fab))
                .perform(click());
        //Test Assertion - Validate that the driver's number is dialed on the screen
        UiObject dialedNumberTextelement = mDevice.findObject(new UiSelector().text("(413) 868-2228"));
        assertThat(dialedNumberTextelement.exists(), equalTo(true));

        //come back to the app as user might do - by pressing back button
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
    }

    //@Test
    public void logOut() {
        //Open the drawer and hit logout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Logout")).perform(click());
    }

    @Test
    public void logInAndSearchDriver() {
        logIn();
        searchDriver();
        logOut();
    }

}
