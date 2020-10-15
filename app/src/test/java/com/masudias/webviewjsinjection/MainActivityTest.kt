package com.masudias.webviewjsinjection

import com.masudias.webviewjsinjection.databinding.ActivityMainBinding
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowWebView
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var binding: ActivityMainBinding

    @Before
    fun setUp() {
        activity =
            Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
        binding = ReflectionHelpers.getField(activity, "binding")
    }

    @After
    fun tearDown() {
        activity.finish()
    }

    @Test
    fun testWebViewSetup() {
        val shadowWebView: ShadowWebView = Shadows.shadowOf(binding.webView)
        Assert.assertEquals(
            "URL is wrong",
            "https://www.google.com",
            shadowWebView.lastLoadedUrl
        )
        Assert.assertTrue(
            "JavaScript is not configured true",
            binding.webView.settings.javaScriptEnabled
        )
        Assert.assertTrue(
            "DOM Storage is not enabled",
            binding.webView.settings.domStorageEnabled
        )
    }
}