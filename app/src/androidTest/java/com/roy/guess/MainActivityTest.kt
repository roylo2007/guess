package com.roy.guess

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Rule
    @JvmField
    val activitytestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun guessWrong(){
        val resources = activitytestRule.activity.resources
        val secret = activitytestRule.activity.secretNumber.number
        for (n in 1..10) {
            if (n != secret) {
                onView(withId(R.id.number)).perform(clearText())
                onView(withId(R.id.number)).perform(typeText(n.toString()))
                onView(withId(R.id.button)).perform(click())
                val message =
                    if (n < secret) resources.getString(R.string.bigger)
                    else resources.getString(R.string.smaller)
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }

}