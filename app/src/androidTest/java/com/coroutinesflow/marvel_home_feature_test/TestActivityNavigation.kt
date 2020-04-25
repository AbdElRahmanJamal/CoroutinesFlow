package com.coroutinesflow.marvel_home_feature_test

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.coroutinesflow.MainActivity
import com.coroutinesflow.R
import com.coroutinesflow.features.heroes_home.view.MarvelHeroesAdapter
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TestActivityNavigation {

    @Test
    fun test_activity_navigation() {

        // SETUP
        ActivityScenario.launch(MainActivity::class.java)

        // VERIFY
        onView(withId(R.id.marvel_heroes_page))
            .check(matches(isDisplayed()))

        //CLICK ON ITEM
        onView(withId(R.id.marvel_character_recView))
            .perform(
                actionOnItemAtPosition<MarvelHeroesAdapter.MarvelCharactersViewHolder>(
                    7,
                    click()
                )
            )

        // VERIFY
        onView(withId(R.id.hero_details_page))
            .check(matches(isDisplayed()))

        pressBack()

        // VERIFY
        onView(withId(R.id.marvel_heroes_page))
            .check(matches(isDisplayed()))
    }

}