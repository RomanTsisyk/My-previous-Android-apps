package com.example.android.codelabs.paging.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.android.codelabs.paging.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4ClassRunner::class)
class CharacterDetailsActivityTest {

//    @get: Rule
//    val activityRule = ActivityScenarioRule(CharacterDetailsActivity::class.java)

    @Rule // third parameter is set to false which means the activity is not started automatically
    @JvmField
    var rule = ActivityTestRule(CharacterDetailsActivity::class.java, true, true)


    @Test
    fun test_appBar_isVisible_onAppLaunch() {
        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isCharacterDetailParsedCorrect() {
        val startIntent = Intent()
        startIntent.putExtra("name", NAME)
        startIntent.putExtra("gender", GENDER)
        startIntent.putExtra("status", STATUS)
        startIntent.putExtra("location_current", LOCATION)
        startIntent.putExtra("location_origin", ORIGIN)
        rule.launchActivity(startIntent)
        onView(withId(R.id.character_detail_name)).check(matches(withText(NAME)))
        onView(withId(R.id.character_detail_status)).check(matches(withText(STATUS)))
        onView(withId(R.id.character_detail_gender)).check(matches(withText(GENDER)))
        onView(withId(R.id.character_detail_location_origin)).check(matches(withText(ORIGIN)))
        onView(withId(R.id.character_detail_location_current)).check(matches(withText(LOCATION)))
    }


    companion object {
        private const val LOCATION = "Earth (Replacement Dimension)"
        private const val NAME = "Rick Sanchez"
        private const val STATUS = "Alive"
        private const val GENDER = "Male"
        private const val ORIGIN = "Earth (C-137)"
    }


}