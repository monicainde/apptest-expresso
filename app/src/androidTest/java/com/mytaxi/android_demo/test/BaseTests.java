package com.mytaxi.android_demo.test;

import com.mytaxi.android_demo.test.pages.LogInPage;

public class BaseTests {
    protected LogInPage logInPage = new LogInPage();

    public void tearDown() {
        logInPage.appLogOut();
    }
}
