package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.databinding.ListItemNewsBinding
import haina.ecommerce.model.ArticlesItem

class AdapterHeadlineNews (private val context: Context, private val newsList: List<ArticlesItem?>?): RecyclerView.Adapter<AdapterHeadlineNews.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNewsBinding.bind(itemView)
        fun bind(item: ArticlesItem){
            with(binding){
                tvTitleNews.text = item.title
                tvMediaName.text = item.author
                Glide.with(context).load(item.urlToImage).into(ivNews)
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