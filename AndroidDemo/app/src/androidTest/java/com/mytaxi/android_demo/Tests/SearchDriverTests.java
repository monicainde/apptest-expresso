package com.mytaxi.android_demo.Tests;

import android.Manifest;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.Tests.pages.LogInPage;
import com.mytaxi.android_demo.Tests.pages.SearchPage;
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
public class SearchDriverTests extends BaseTests {
    private String DRIVER_NAME;
    private String DRIVER_NUMBER;
    private LogInPage logInPage;
    private SearchPage searchPage;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "Sarah Scott", "(413) 868-2228" }
        });
    }

    //Constructor
    public SearchDriverTests(String driverName, String driverNumber){
        this.DRIVER_NAME = driverName;
        this.DRIVER_NUMBER = driverNumber;
        logInPage = new LogInPage();
        searchPage = new SearchPage();
    }

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initialSetUp(){
        setup();
    }

    @Test
    public void logInAndPartialSearchDriver() {
        Espresso.registerIdlingResources(mActivityRule.getActivity().getIdlingResource());
        logInPage.appLogIn("crazydog335","venture");
        searchPage.partialSearchDriver(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    //Test to search full drivername
    @Test
    public void logInAndSearchDriver() {
        Espresso.registerIdlingResources(mActivityRule.getActivity().getIdlingResource());
        logInPage.appLogIn("crazydog335","venture");
        searchPage.searchDriver(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    @After
    public void tearDown(){
        logInPage.appLogOut();
        Espresso.unregisterIdlingResources(mActivityRule.getActivity().getIdlingResource());
    }
}
