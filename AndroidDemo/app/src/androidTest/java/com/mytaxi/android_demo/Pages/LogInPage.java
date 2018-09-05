package com.mytaxi.android_demo.Pages;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.Utils.WaitHelper;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LogInPage {

    public void appLogIn(String USERNAME,String PASSWORD) {
        //Enter Username , Password and Click Login Button
        onView(ViewMatchers.withId(R.id.edt_username))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        //Sometime the login takes too long and landing page does not load immediately, waitFor will wait till textSearch is loaded.
        onView(isRoot()).perform(WaitHelper.waitFor(TimeUnit.SECONDS.toMillis(6)));

        // Check that landing page is loaded
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    public void appLogOut() {
        //Open the drawer and hit logout
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Logout")).perform(click());
    }
}
