package com.example.android.codelabs.paging.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.testutil.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    // RecycleView comes into view
    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(withId(R.id.list)).check(matches(isDisplayed()))
    }

    // Select list item -> CharacterDetail activity comes into view
    @Test
    fun test_selectListItem_isCharacterDetailActivityVisible() {
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<CharacterViewHolder>(0, click()))
        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
    }

    // Select list item -> correct character detail in view
    @Test
    fun test_selectListItem_isCharacterDetailParsedCorrect() {
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<CharacterViewHolder>(0, click()))
        onView(withId(R.id.character_detail_name)).check(matches(withText(NAME)))
        onView(withId(R.id.character_detail_status)).check(matches(withText(STATUS)))
        onView(withId(R.id.character_detail_gender)).check(matches(withText(GENDER)))
        onView(withId(R.id.character_detail_location_origin)).check(matches(withText(ORIGIN)))
        onView(withId(R.id.character_detail_location_current)).check(matches(withText(Companion.LOCATION)))

    }

    // Select list item -> get back by pressBack
    @Test
    fun test_selectListItem_isCharacterDetailActivityVisible_backButtonPressed() {
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<CharacterViewHolder>(0, click()))
        onView(withId(R.id.fab_back)).perform(click())
        onView(withId(R.id.list)).check(matches(isDisplayed()))

    }

    companion object {
        private const val LOCATION = "Earth (Replacement Dimension)"
        private const val NAME = "Rick Sanchez"
        private const val STATUS = "Alive"
        private const val GENDER = "Male"
        private const val ORIGIN = "Earth (C-137)"
    }
}