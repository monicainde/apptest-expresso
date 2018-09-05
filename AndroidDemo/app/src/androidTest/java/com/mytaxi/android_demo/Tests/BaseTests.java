package com.mytaxi.android_demo.Tests;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class BaseTests {
    public static UiDevice mDevice_base = UiDevice.getInstance(getInstrumentation());
    public static void allowCurrentPermission() {

        UiObject allowButton = mDevice_base.findObject(new UiSelector().text("ALLOW"));
        //if its a first time execution of the test, then only you get this popup - Allowand go ahead
        if(allowButton.exists()) {
            try {
                allowButton.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Permission Dialog exists, Accepting it!");
        }
        else
            System.out.println("Permission Dialog does not exist, Do nothing!");
    }

    public static void setup() {
        allowCurrentPermission();
    }
}
