package com.mytaxi.android_demo.Tests;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogInTests {
    private String USERNAME;
    private String PASSWORD;
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

    public LogInTests(String username, String password){
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /*
    public LogInTests(){
        this.USERNAME = "crazydog335";
        this.PASSWORD = "venture";
    }*/

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public void allowCurrentPermission() throws UiObjectNotFoundException {
        UiObject allowButton = mDevice.findObject(new UiSelector().text("ALLOW"));
        //if its a first time execution of the test, then only you get this popup - Allowand go ahead
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

    @Test
    public void logIn() {
        // Type username/password and then press the log in button.
        onView(ViewMatchers.withId(R.id.edt_username))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        //Sometime the login takes too long and landing page does not load immediately, waitForId will wait till textSearch is loaded.
        onView(isRoot()).perform(WaitHelper.waitForId(R.id.textSearch, TimeUnit.SECONDS.toMillis(10)));

        // Check that landing page is loaded
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    @Test
    public void logOut() {
        //Open the drawer and hit logout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Logout")).perform(click());
    }

}
