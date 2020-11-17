package haina.ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.model.News
import kotlinx.android.synthetic.main.list_item_news.view.*

class AdapterNews (private val newsList: List<News>): RecyclerView.Adapter<AdapterNews.Holder>(){

    class Holder (view: View): RecyclerView.ViewHolder(view){
        val title_news = view.tv_title_news
        val media_name = view.tv_media_name
        val image_news = view.iv_news
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false)

        return Holder(v)
    }

    override fun onBindViewHolder(holder: AdapterNews.Holder, position: Int) {
        val news: News = newsList[position]

        holder.title_news.text = news.title
        holder.media_name.text = news.source
        holder.image_news.setImageResource(news.image)
    }

    override fun getItemCount(): Int = newsList.size
}