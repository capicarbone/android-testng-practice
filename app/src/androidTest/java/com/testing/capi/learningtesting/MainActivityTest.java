package com.testing.capi.learningtesting;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import io.appflate.restmock.RESTMockServer;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(
            MainActivity.class,
            true,
            false
    );

    @Before
    public void setup(){
        RESTMockServer.reset();
        APIConf.API_URL = RESTMockServer.getUrl();
    }

    @Test
    public void booksReceived() throws Exception {

        RESTMockServer.whenGET(pathContains("books"))
                .delay(TimeUnit.SECONDS, 2)
                .thenReturnFile(200, "books/all.json");

        rule.launchActivity(null);
        Thread.sleep(1000);

        // Checks

        onView(withId(R.id.books_list)).check(matches(isDisplayed()));
        onView(withId(R.id.empty_message)).check(matches(not(isDisplayed())));

        onData(BookTitleMatcher.matchesBookTitle("Romeo y Julieta")).check(matches(isDisplayed()));

    }

    @Test
    public void emptyResponse() throws Exception{
        RESTMockServer.whenGET(pathContains("books"))
                .delay(TimeUnit.SECONDS, 2)
                .thenReturnFile(200, "empty_array.json");

        rule.launchActivity(null);
        Thread.sleep(1000);

        onView(withId(R.id.books_list)).check(matches(not(isDisplayed())));
        onView(withId(R.id.empty_message)).check(matches(isDisplayed()));
    }

    @Test
    public void networkError() throws Exception{
        RESTMockServer.whenGET(pathContains("books"))
                .delay(TimeUnit.SECONDS, 2)
                .thenReturnFile(500, "empty_array.json");

        rule.launchActivity(null);
        Thread.sleep(1000);

        onView(withId(R.id.progress)).check(matches(not(isDisplayed())));

        //onView(withText(R.string.network_error)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

}
