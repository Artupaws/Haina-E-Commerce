package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemRoomDarmaBinding
import haina.ecommerce.databinding.ListItemRoomHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.RoomsItem
import haina.ecommerce.model.hotels.newHotel.RoomsItemDarma

class AdapterListRoomDarma(val context: Context, private val listRoom: List<RoomsItemDarma?>?, private val itemAdapterCallback:ItemAdapterCallback):
    RecyclerView.Adapter<AdapterListRoomDarma.Holder>() {
    private val helper:Helper = Helper


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRoomDarmaBinding.bind(view)
        fun bind(itemHaina: RoomsItemDarma, itemAdapterCallback: ItemAdapterCallback){
            binding.apply {
                tvNameRoom.text = itemHaina.name
                tvRoomPrice.text=helper.convertToFormatMoneyIDRFilter(itemHaina.price.toString())
                var statusBreakfast = itemHaina.breakfast
                val icon = HtmlCompat.fromHtml("<img${itemHaina.image}>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                Glide.with(context).load(icon).into(ivRoomHotel)
                statusBreakfast = if (!statusBreakfast?.toLowerCase()?.contains("breakfast")!!){
                    "Breakfast : ${context.getString(R.string.breakfast_status_no)}"
                } else {
                    "Breakfast : ${context.getString(R.string.breakfast_status_yes)}"
                }
                tvStatusBreakfast.text = statusBreakfast
                btnSelect.setOnClickListener {
                    itemAdapterCallback.onClick(btnSelect, itemHaina)
                }
                if(itemHaina.facilites?.size!=0){
                    setupListFacilities(binding, itemHaina.facilites)
                }else{
                    lnFacilities.visibility=View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListRoomDarma.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRoomDarmaBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListRoomDarma.Holder, position: Int) {
        val photo: RoomsItemDarma = listRoom?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listRoom?.size!!

    private fun setupListFacilities(binding: ListItemRoomDarmaBinding, dataFacilities:List<String?>?){
        if (dataFacilities != null){
            binding.rvFacilitiesRoom.apply {
                adapter = AdapterFacilitiesRoom(context, dataFacilities)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:RoomsItemDarma)
    }
}