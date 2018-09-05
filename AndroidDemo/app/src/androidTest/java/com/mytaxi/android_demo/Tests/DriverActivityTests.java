package com.mytaxi.android_demo.Tests;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.R;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
@LargeTest
public class DriverActivityTests {
    private String DRIVER_NAME;
    private String DRIVER_NUMBER;
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    public LogInTests logIn_Tests;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Sarah Scott", "(413) 868-2228" }
        });
    }

    //Constructor
    public DriverActivityTests(String driverName, String driverNumber){
        this.DRIVER_NAME = driverName;
        this.DRIVER_NUMBER = driverNumber;
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initialSetUp(){
        //set up log in parameters
        logIn_Tests = new LogInTests("crazydog335","venture");
    }

    @After
    public void tearDown(){
        logIn_Tests.logOut();
    }

    public void searchDriver() {
        // Type text and then press the search button.
        String driverSearchString= DRIVER_NAME.substring(0,2).toLowerCase();
        onView(ViewMatchers.withId(R.id.textSearch))
                .perform(typeText(driverSearchString), closeSoftKeyboard());

        //select the 2nd result (via the name) from the list
        onView(withText(DRIVER_NAME))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
        //Click on Call Button
        onView(withId(R.id.fab))
                .perform(click());
        //Test Assertion - Validate that the driver's number is dialed on the screen
        UiObject dialedNumberTextelement = mDevice.findObject(new UiSelector().text(DRIVER_NUMBER));
        assertThat(dialedNumberTextelement.exists(), equalTo(true));

        //come back to the main app, from contacts app
        goBackToAppHome();
    }

    public void goBackToAppHome() {
        //come back to the app as user might do - by pressing back button
        UiObject topNavigationBar = mDevice.findObject(new UiSelector().text("mytaxi demo"));
        int backpressCounter = 0; //to avoid infinite loops
        while(!topNavigationBar.exists()) {
            mDevice.pressBack();
            backpressCounter++;
            if(backpressCounter>10)
                break;
        }
    }

    @Test
    public void logInAndSearchDriver() {
        logIn_Tests.logIn();
        searchDriver();
    }

}
