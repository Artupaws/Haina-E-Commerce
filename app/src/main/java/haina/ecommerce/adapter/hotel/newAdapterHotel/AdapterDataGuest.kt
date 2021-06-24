package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ViewUtils
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemDataPassengerBinding
import haina.ecommerce.model.hotels.newHotel.DataGuest
import haina.ecommerce.room.roomdatapassenger.DataPassenger

class AdapterDataGuest(val context: Context, private val listDataPassenger: List<DataGuest?>?,
                       private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterDataGuest.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemDataPassengerBinding.bind(view)
        fun bind(itemHaina: DataGuest?, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                tvBirthdate.visibility = View.GONE
                tvIdcardNumber.visibility = View.GONE
                val nameAndTitle = "${itemHaina?.title}, ${itemHaina?.first_name} ${itemHaina?.last_name}"
                tvNamePassenger.text = nameAndTitle
                tvActionDelete.setOnClickListener {
                    itemAdapterCallback.onClick(tvActionDelete, itemHaina!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDataGuest.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDataPassengerBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDataGuest.Holder, position: Int) {
        val photo: DataGuest? = listDataPassenger?.get(position)
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listDataPassenger?.size!!

    interface ItemAdapterCallback{
        fun onClick(view:View, data: DataGuest)
    }

}