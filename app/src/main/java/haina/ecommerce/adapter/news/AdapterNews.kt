package haina.ecommerce.adapter.news

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
import haina.ecommerce.model.news.DataNews
import haina.ecommerce.model.news.DataNewsTable
import haina.ecommerce.view.webview.WebViewActivity

class AdapterNews (private val context: Context, private val newsList: List<DataNewsTable?>?, private val itemAdapterCallback: ItemAdapterCallback): RecyclerView.Adapter<AdapterNews.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNewsBinding.bind(itemView)
        private var url: String = ""
        fun bind(item: DataNewsTable, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                tvTitleNews.text = item.title
                tvMediaName.text = item.sourceName
                Glide.with(context).load(item.image).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivNews)
                linearList.setOnClickListener {
                    itemAdapterCallback.onClick(binding.linearList, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNewsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news: DataNewsTable = newsList?.get(position)!!
        holder.bind(news, itemAdapterCallback)
    }

    override fun getItemCount(): Int = newsList?.size!!

    interface ItemAdapterCallback{
        fun onClick(view:View, data:DataNewsTable)
    }
}