package com.mytaxi.android_demo.Tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;

import com.mytaxi.android_demo.Pages.LogInPage;
import com.mytaxi.android_demo.Pages.SearchPage;

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

@RunWith(Parameterized.class)
@LargeTest
public class DriverActivityTests extends BaseTests {
    private String DRIVER_NAME;
    private String DRIVER_NUMBER;
    private UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    private LogInPage logInPage;
    private SearchPage searchPage;

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
        logInPage = new LogInPage();
        searchPage = new SearchPage();
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initialSetUp(){
        setup();
    }

    @After
    public void tearDown(){
        logInPage.appLogOut();
    }



    @Test
    public void logInAndSearchDriver() {
        logInPage.appLogIn("crazydog335","venture");
        searchPage.searchDriver(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

}
