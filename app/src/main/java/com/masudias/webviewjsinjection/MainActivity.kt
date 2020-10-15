package com.masudias.webviewjsinjection

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.masudias.webviewjsinjection.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_main)

        binding.webView!!.settings.javaScriptEnabled = true
        binding.webView!!.settings.domStorageEnabled = true
        binding.webView!!.webViewClient = HelloWebViewClient()
        binding.webView!!.loadUrl("https://www.google.com")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView!!.canGoBack()) {
            binding.webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class HelloWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            runOnUiThread {
                view?.evaluateJavascript(
                    "(function() {"
                            + "localStorage.setItem(\"some-info\", \"Some stored info\");"
                            + "console.log('Stored some information using JS injection');" // You should be able to see this in the logcat
                            + "return localStorage.getItem(\"some-info\");"
                            + "})();"
                ) { storedInfo ->
                    Log.d(MainActivity::class.simpleName, "The information stored is: $storedInfo")
                }
            }
        }
    }
}