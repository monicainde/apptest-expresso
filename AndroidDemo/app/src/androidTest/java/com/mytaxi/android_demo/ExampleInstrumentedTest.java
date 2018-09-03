package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import android.app.Activity;
import com.mytaxi.android_demo.activities.MainActivity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;


import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
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
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void beforeEachTest(){
        System.out.println("Test Started");
    }


    @AfterClass
    public static void afterEachTest(){
        System.out.println("Class Ended");
        logOut();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }



    public static void logOut() {
        //Open the drawer and hit logout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Logout")).perform(click());
    }

    @Test
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

}
