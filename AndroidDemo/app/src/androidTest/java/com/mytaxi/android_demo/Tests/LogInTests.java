package com.mytaxi.android_demo.Tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.mytaxi.android_demo.Pages.LogInPage;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
@LargeTest
public class LogInTests extends BaseTests{
    private String USERNAME;
    private String PASSWORD;
    private LogInPage logInPage;


    public LogInTests(String username, String password){
        this.USERNAME = username;
        this.PASSWORD = password;
        this.logInPage = new LogInPage();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "crazydog335", "venture" }
        });
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void beforeEachTest() {
        setup();
    }

    @Test
    public void logIn() {
        // From Page-class: Type username/password and then press the log in button & Verify.
        logInPage.appLogIn(USERNAME,PASSWORD);
    }

    @After
    public void logOut() {
        logInPage.appLogOut();
    }

}
