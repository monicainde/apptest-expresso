package com.mytaxi.android_demo.test.pages;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LogInPage {
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    public void appLogIn(String USERNAME,String PASSWORD) {
        //Enter Username , Password and Click Login Button
        onView(ViewMatchers.withId(R.id.edt_username))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
    }

    public void appLogOut() {
        UiObject topNavigationBar = mDevice.findObject(new UiSelector().text("mytaxi demo"));
        //check if user is logged in
        if(topNavigationBar.exists()){
            //Open the drawer and hit logout
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Logout")).perform(click());
        }
    }
}
