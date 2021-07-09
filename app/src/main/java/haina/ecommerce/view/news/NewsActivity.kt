package haina.ecommerce.view.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.news.AdapterNews
import haina.ecommerce.databinding.ActivityNewsBinding
import haina.ecommerce.model.news.DataNews
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.webview.WebViewActivity

class NewsActivity : AppCompatActivity(), NewsContract, AdapterNews.ItemAdapterCallback {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var presenter: NewsPresenter
    private lateinit var sharedPref: SharedPreferenceHelper
    private var langParams: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferenceHelper(this)
        presenter = NewsPresenter(this, this)
        langParams = sharedPref.getValueString(Constants.LANGUAGE_APP).toString()
        setPresenterBasedLanguage(langParams)
        refresh(langParams)
    }

    private fun refresh(language: String) {
        binding.swipeRefresh.setOnRefreshListener {
            when (language) {
                "en" -> {
                    presenter.getNews("eng")
                }
                "zh" -> {
                    presenter.getNews("zho")
                }
                else -> {
                    presenter.getNews("eng")
                }
            }
        }
    }

    private fun setPresenterBasedLanguage(language: String) {
        when (language) {
            "en" -> {
                presenter.getNews("eng")
            }
            "zh" -> {
                presenter.getNews("zho")
            }
            else -> {
                presenter.getNews("eng")
            }
        }
    }

    override fun messageGetNews(msg: String) {
        Log.d("news", msg)
        if (!msg.contains("Success")) {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getNews(data: List<DataNews?>?) {
        binding.rvNews.apply {
            adapter = AdapterNews(this@NewsActivity, data, this@NewsActivity)
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(view: View, data: DataNews) {
        when (view.id) {
            R.id.linear_list -> {
                val intentToWeb = Intent(applicationContext, DetailNewsActivity::class.java)
                    .putExtra("data", data)
                startActivity(intentToWeb)
            }
        }
    }
}