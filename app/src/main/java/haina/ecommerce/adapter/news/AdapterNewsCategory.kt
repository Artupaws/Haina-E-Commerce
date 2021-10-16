package haina.ecommerce.adapter.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.adapter.notification.AdapterNotificationCategory
import haina.ecommerce.databinding.ListItemNotificationCategoryBinding
import haina.ecommerce.model.news.NewsCategory
import haina.ecommerce.model.notification.Notificationcategory
import haina.ecommerce.preference.SharedPreferenceHelper

class AdapterNewsCategory(private val context: Context, private val categorylist: List<NewsCategory?>?): RecyclerView.Adapter<AdapterNewsCategory.Holder>(){

    private var index:Int = 0
    private var responseId:Int =0
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var sharedPref: SharedPreferenceHelper

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNotificationCategoryBinding.bind(itemView)
        fun bind(itemHaina: NewsCategory){
            with(binding){
                tvTitleCategory.text = itemHaina.name
                linearClick.setOnClickListener {
                    index = adapterPosition
                    notifyDataSetChanged()
                }

                if (index == adapterPosition){
                    viewSelected.setBackgroundResource(R.color.yellow)

                    val setCategory = Intent("CategoryFilter")
                        .putExtra("IdCategory", itemHaina.id)
                    broadcaster?.sendBroadcast(setCategory)
                } else {
                    viewSelected.setBackgroundResource(R.color.black_60)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNewsCategory.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNotificationCategoryBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterNewsCategory.Holder, position: Int) {
        val category: NewsCategory = categorylist?.get(position)!!
        holder.bind(category)
    }

    override fun getItemCount(): Int = categorylist?.size!!
//
//    private fun setLanguage(codeLanguage:String?, binding: ListItemNotificationCategoryBinding, data: DataItemHaina){
//        when (codeLanguage){
//            "zh" -> {
//                binding.tvTitleCategoryJob.text = data.nameZh
//            }
//            "en" -> {
//                binding.tvTitleCategoryJob.text = data.name
//            }
//        }
//
//    }
}