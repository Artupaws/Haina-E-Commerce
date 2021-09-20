package haina.ecommerce.adapter.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemNotificationCategoryBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.notification.Notificationcategory
import haina.ecommerce.model.service.CategoryService
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class AdapterNotificationCategory(private val context: Context, private val categorylist: List<Notificationcategory?>?): RecyclerView.Adapter<AdapterNotificationCategory.Holder>(){

    private var index:Int = 0
    private var responseId:Int =0
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var sharedPref: SharedPreferenceHelper

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ListItemNotificationCategoryBinding.bind(itemView)
        fun bind(itemHaina: Notificationcategory){
            with(binding){
                tvTitleCategory.text = itemHaina.name
                if (tvTitleCategory.text == "All Category"){
                    ivImageCategory.setImageResource(R.drawable.ic_add)
                } else {
                    Glide.with(context).load(itemHaina.img).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivImageCategory)
                }
                linearClick.setOnClickListener {
                    index = adapterPosition
                    notifyDataSetChanged()
                }

                if (index == adapterPosition){
                    viewSelected.setBackgroundResource(R.color.yellow)

                    val setCategory = Intent("CategoryFilter")
                        .putParcelableArrayListExtra("NotificationList", itemHaina.notif as ArrayList)
                    broadcaster?.sendBroadcast(setCategory)
                } else {
                    viewSelected.setBackgroundResource(R.color.black_60)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNotificationCategory.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNotificationCategoryBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterNotificationCategory.Holder, position: Int) {
        val category: Notificationcategory = categorylist?.get(position)!!
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