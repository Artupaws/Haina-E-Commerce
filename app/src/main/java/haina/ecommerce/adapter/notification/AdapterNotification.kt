package haina.ecommerce.adapter.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemNotificationBinding
import haina.ecommerce.model.notification.DataItemNotification


class AdapterNotification(val context: Context, private val listNotification: List<DataItemNotification?>?, private val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterNotification.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemNotificationBinding.bind(view)
        fun bind(itemHaina: DataItemNotification, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                tvDateNotification.text = itemHaina.date
//                tvCategoryNotification.text = itemHaina.notificationcategory?.name
                tvTitleNotification.text = itemHaina.title
                tvDescriptionNotification.text = itemHaina.body
                itemView.setOnClickListener {
                    itemAdapterCallback.onAdapterClick(itemView, itemHaina)
                }
                if(itemHaina.opened==0){
                    linearNotification.setBackgroundColor(0x8DFDDB3A.toInt())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNotification.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNotificationBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterNotification.Holder, position: Int) {
        val notification: DataItemNotification = listNotification?.get(position)!!
        holder.bind(notification, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listNotification?.size!!

    interface ItemAdapterCallback{
        fun onAdapterClick(view: View, data:DataItemNotification)
    }
}