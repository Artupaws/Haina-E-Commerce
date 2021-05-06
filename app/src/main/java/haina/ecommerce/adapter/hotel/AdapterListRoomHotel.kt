package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemRoomHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.RoomHotel
import haina.ecommerce.model.hotels.RoomsItem

class AdapterListRoomHotel(val context: Context, private val listRoom: List<RoomsItem?>?, private val itemAdapterCallback:ItemAdapterCallback):
    RecyclerView.Adapter<AdapterListRoomHotel.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRoomHotelBinding.bind(view)
        fun bind(itemHaina: RoomsItem, itemAdapterCallback: ItemAdapterCallback){
            binding.apply {
                tvNameRoom.text = itemHaina.roomName
                val roomsLeft = "Room(s) Left : ${itemHaina.roomTotal}"
                tvRestRoom.text = roomsLeft
                val price = helper.convertToFormatMoneyIDRFilter(itemHaina.roomPrice.toString())
                tvPriceRoom.text = price
                tvTypeBed.text = itemHaina.roomBedType
                btnBookRoom.setOnClickListener{ itemAdapterCallback.onClick(binding, itemHaina) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListRoomHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRoomHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListRoomHotel.Holder, position: Int) {
        val photo: RoomsItem = listRoom?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listRoom?.size!!

    interface ItemAdapterCallback{
        fun onClick(binding: ListItemRoomHotelBinding, data:RoomsItem)
    }
}