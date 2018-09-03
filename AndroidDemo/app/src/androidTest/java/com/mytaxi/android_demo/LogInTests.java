package com.mytaxi.android_demo;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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

    @Test
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

}
