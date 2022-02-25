package com.kutugondrong.github.screen

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.kutugondrong.github.helper.MockServerDispatcher
import com.kutugondrong.github.R
import com.kutugondrong.github.helper.asAndroidX
import com.kutugondrong.github.helper.waitViewShown
import com.kutugondrong.github.helper.launchFragmentInHiltContainer
import com.kutugondrong.github.screen.detail.DetailUserFragment
import com.kutugondrong.github.screen.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@UninstallModules(AppModule::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: GithubFragmentFactoryAndroidTest

    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var okHttp: OkHttpClient

    @Before
    fun init() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
        IdlingRegistry.getInstance()
            .register(OkHttp3IdlingResource.create("okhttp", okHttp).asAndroidX())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testVisibilityHomeFragment() {
        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory)
        waitViewShown(withId(R.id.progressHome), false)
        onView(withId(R.id.recycle_user_github)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testOpenDetailUser_thenNNavigateToDetailUserFragment() {
        var frag: HomeFragment? = null
        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory) {
            frag = this
        }
        waitViewShown(withId(R.id.progressHome), false)
        onView(withId(R.id.recycle_user_github)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.recycle_user_github)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.scrollTo())
        )
        val data = frag?.adapter?.getItemByPosition(0)

        val bundle = Bundle()
        bundle.putParcelable("user", data)
        launchFragmentInHiltContainer<DetailUserFragment>(fragmentFactory = fragmentFactory,
            fragmentArgs = bundle)
        waitViewShown(withId(R.id.progressDetailUser), false)
    }

}



