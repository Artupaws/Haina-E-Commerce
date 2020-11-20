package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemNewsBinding
import haina.ecommerce.model.News

class AdapterNews (private val newsList: List<News>): RecyclerView.Adapter<AdapterNews.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNewsBinding.bind(itemView)
        fun bind(item: News){
            with(binding){
                tvTitleNews.text = item.title
                tvMediaName.text = item.source
                ivNews.setImageResource(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterNews.Holder, position: Int) {
        val news: News = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList.size
}