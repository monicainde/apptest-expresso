package com.mytaxi.android_demo.Tests.pages;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.activities.MainActivity;

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

public class SearchPage {
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    public void partialSearchDriver(String DRIVER_NAME, String DRIVER_NUMBER, ActivityTestRule<MainActivity> mActivityRule) {
        //shortening search string to load hints - partial search string
        String driverSearchString= DRIVER_NAME.substring(0,2).toLowerCase();

        // Type text and then press the call button.
        enterDriverNameToSearch(driverSearchString);
        selectDriverAndCall(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    public void searchDriver(String DRIVER_NAME, String DRIVER_NUMBER, ActivityTestRule<MainActivity> mActivityRule) {
        // Type text and then press the call button.
        enterDriverNameToSearch(DRIVER_NAME);
        selectDriverAndCall(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    public void selectDriverAndCall(String DRIVER_NAME, String DRIVER_NUMBER, ActivityTestRule<MainActivity> mActivityRule){
        selectSearchResultByText(DRIVER_NAME,mActivityRule);
        callDriver();

        //Test Assertion - Validate that the driver's number is dialed on the screen
        UiObject dialedNumberTextelement = mDevice.findObject(new UiSelector().text(DRIVER_NUMBER));
        assertThat(dialedNumberTextelement.exists(), equalTo(true));

        //come back to the main app, from contacts app
        goBackToAppHome();
    }

    public void enterDriverNameToSearch(String driverSearchString) {
        onView(ViewMatchers.withId(R.id.textSearch))
                .perform(typeText(driverSearchString), closeSoftKeyboard());
    }

    public void selectSearchResultByText(String DRIVER_NAME, ActivityTestRule<MainActivity> mActivityRule) {
        //select the 2nd result (via the name) from the list
        onView(withText(DRIVER_NAME))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }

    public void callDriver() {
        //Click on Call Button
        onView(withId(R.id.fab))
                .perform(click());
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
}
