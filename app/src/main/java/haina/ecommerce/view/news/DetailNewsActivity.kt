package haina.ecommerce.view.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ActivityDetailNewsBinding
import haina.ecommerce.model.news.DataNews

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataNews = intent.getParcelableExtra<DataNews>("data")
        setNewsToView(dataNews!!)

        binding.toolbarDetailNews.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setNewsToView(data:DataNews){
        binding.tvTitleNews.text = data.title
        Glide.with(applicationContext).load(data.image).skipMemoryCache(true).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(binding.ivImageNews)
        binding.tvSourceNews.text = data.sourceName
        binding.tvNewsPublish.text = data.date
        binding.tvDescriptionNews.text = data.body
    }
}