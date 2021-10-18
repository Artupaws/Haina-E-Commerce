package haina.ecommerce.view.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.TabAdapterForum
import haina.ecommerce.adapter.news.AdapterNews
import haina.ecommerce.adapter.news.AdapterNewsCategory
import haina.ecommerce.adapter.news.TabAdapterNewsCategory
import haina.ecommerce.adapter.notification.AdapterNotification
import haina.ecommerce.adapter.notification.AdapterNotificationCategory
import haina.ecommerce.databinding.ActivityNewsBinding
import haina.ecommerce.model.news.DataNews
import haina.ecommerce.model.news.DataNewsTable
import haina.ecommerce.model.news.NewsCategory
import haina.ecommerce.model.notification.DataItemNotification
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.webview.WebViewActivity
import timber.log.Timber

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
        presenter.getNewsCategory()

        binding.toolbarNews.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    override fun messageGetNews(msg: String) {
        Log.d("news", msg)
        if (!msg.contains("Success")) {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getNewsCategory(data: List<NewsCategory?>?) {
        binding.vpNews.adapter = TabAdapterNewsCategory(supportFragmentManager, 0, data!!)
        binding.tabNews.setupWithViewPager(binding.vpNews)
    }


    override fun onClick(view: View, data: DataNewsTable) {
        when (view.id) {
            R.id.linear_list -> {
                val intentToWeb = Intent(applicationContext, DetailNewsWebViewActivity::class.java)
                    .putExtra("data", data)
                startActivity(intentToWeb)
            }
        }
    }
}