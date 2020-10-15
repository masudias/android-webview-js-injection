# android-webview-js-injection
A simple activity that loads a WebView and we store some information in the local storage of the WebView using Javascript injection

* Step 1: Add a `WebView` in the layout. Let us assume the id of the `WebView` is `web_view`. 
* Step 2: Get the reference of the `WebView` in the activity/fragment and enabled dom storage and Javascript. 

```kotlin
binding.webView!!.settings.javaScriptEnabled = true
binding.webView!!.settings.domStorageEnabled = true
binding.webView!!.webViewClient = HelloWebViewClient()
binding.webView!!.loadUrl("https://www.google.com")
```

* Step 3: Implement the `HelloWebViewClient` as below. 

**Important thing to note that, the `evaluateJavascript` needs to be called in the UI thread.**

```kotlin
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
```

