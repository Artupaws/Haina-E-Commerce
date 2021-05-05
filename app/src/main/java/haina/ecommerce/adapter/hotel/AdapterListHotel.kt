package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemHotelsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.DataItem


class AdapterListHotel(val context: Context, private val listHotel: List<DataItem?>?,
                       private val itemAdapterCallback:ItemAdapterCallBack):
    RecyclerView.Adapter<AdapterListHotel.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHotelsBinding.bind(view)
        fun bind(itemHaina: DataItem, itemAdapterCallback:ItemAdapterCallBack){
            with(binding){
                tvNameHotel.text = itemHaina.hotelName
                tvLocationHotel.text = itemHaina.hotelAddress
                val startPrice = helper.convertToFormatMoneyIDRFilter(itemHaina.startingPrice!!)
                tvStartPrice.text = startPrice
                cvClick.setOnClickListener { itemAdapterCallback.onClick(binding.cvClick, itemHaina) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHotel.Holder, position: Int) {
        val photo: DataItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

    interface ItemAdapterCallBack{
        fun onClick(view: View, dataHotel: DataItem)
    }


}