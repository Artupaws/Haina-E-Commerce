package haina.ecommerce.view.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityNewsWebviewBinding
import haina.ecommerce.model.news.DataNewsTable

class DetailNewsWebViewActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewsWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataNews = intent.getParcelableExtra<DataNewsTable>("data")




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
                    binding.ivLoading.visibility = View.GONE
                }
                return
            }
        }
        binding.toolbar3.title = getString(R.string.detail_news)
        binding.webView.loadUrl(dataNews!!.url!!)
    }
}