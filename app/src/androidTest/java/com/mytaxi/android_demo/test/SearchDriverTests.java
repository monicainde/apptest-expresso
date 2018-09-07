package com.mytaxi.android_demo.test;

import android.Manifest;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.test.pages.SearchPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(Parameterized.class)
@LargeTest
public class SearchDriverTests extends BaseTests {
    private String DRIVER_NAME;
    private String DRIVER_NUMBER;
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
        searchPage = new SearchPage();
    }

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Espresso.registerIdlingResources(mActivityRule.getActivity().getIdlingResource());
        logInPage.appLogIn("crazydog335","venture");
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    @Test
    public void logInAndPartialSearchDriver() {
        searchPage.partialSearchDriver(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    /*
     Test to search full drivername
    * */
    @Test
    public void logInAndSearchDriver() {
        searchPage.searchDriver(DRIVER_NAME,DRIVER_NUMBER,mActivityRule);
    }

    @After
    public void cleanUp(){
        tearDown();
        Espresso.unregisterIdlingResources(mActivityRule.getActivity().getIdlingResource());
    }
}
