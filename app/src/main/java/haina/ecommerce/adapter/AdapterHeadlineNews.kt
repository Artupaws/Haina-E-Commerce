package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.databinding.ListItemNewsBinding
import haina.ecommerce.model.ArticlesItem
import haina.ecommerce.view.webview.WebViewActivity

class AdapterHeadlineNews (private val context: Context, private val newsList: List<ArticlesItem?>?): RecyclerView.Adapter<AdapterHeadlineNews.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNewsBinding.bind(itemView)
        private var url: String = ""
        fun bind(item: ArticlesItem){
            with(binding){
                url = item.url.toString()
                tvTitleNews.text = item.title
                tvMediaName.text = item.author
                Glide.with(context).load(item.urlToImage).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivNews)
                linearList.setOnClickListener {
                    val intent = Intent(context, WebViewActivity::class.java)
                    intent.putExtra("url", url)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHeadlineNews.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterHeadlineNews.Holder, position: Int) {
        val news: ArticlesItem = newsList?.get(position)!!
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList?.size!!
}