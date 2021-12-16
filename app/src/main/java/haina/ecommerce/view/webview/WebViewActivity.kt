package haina.ecommerce.view.webview

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityWebviewBinding
import haina.ecommerce.model.ArticlesItem

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private var intentFrom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getUrl: String = intent.extras?.get("url").toString()
        intentFrom = intent.getStringExtra("intentFrom")
        binding.toolbar3.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbar3.setNavigationOnClickListener { onBackPressed() }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                binding.ivLoading.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (binding.webView.progress == 100){
                    binding.ivLoading.visibility = GONE
                }
                return
            }
        }
        when (intentFrom) {
            "document" -> {
                binding.webView.loadUrl("http://docs.google.com/gview?embedded=true&url=${getUrl}")
                binding.toolbar3.title = getString(R.string.user_resume)
            }
            "help" -> {
                binding.toolbar3.title = getString(R.string.help_support)
                binding.webView.loadUrl(getUrl)
            }
            else -> {
                binding.toolbar3.title = getString(R.string.detail_news)
                binding.webView.loadUrl(getUrl)
            }
        }

    }
}