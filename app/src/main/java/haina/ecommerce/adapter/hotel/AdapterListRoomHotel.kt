package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemRoomHotelBinding
import haina.ecommerce.model.hotels.Facilities
import haina.ecommerce.model.hotels.RoomHotel
import haina.ecommerce.view.hotels.selectdate.SelectDateHotelActivity

class AdapterListRoomHotel(val context: Context, private val listRoom: List<RoomHotel?>?):
    RecyclerView.Adapter<AdapterListRoomHotel.Holder>() {

    var onItemClick:(String, String)-> Unit = {priceRoom:String, typeRoom:String ->}

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemRoomHotelBinding.bind(view)
        fun bind(itemHaina: RoomHotel){
            with(binding){
                tvNameRoom.text = itemHaina.typeRoom
                val roomsLeft = "Room(s) Left : ${itemHaina.roomsLeft}"
                tvRestRoom.text = roomsLeft
                tvPriceRoom.text = itemHaina.price
                tvTypeBed.text = itemHaina.typeBed
                btnBookRoom.setOnClickListener {
                    onItemClick(itemHaina.price, itemHaina.typeRoom)
//                    val selectDate = Intent(context, SelectDateHotelActivity::class.java)
//                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(selectDate)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListRoomHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRoomHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListRoomHotel.Holder, position: Int) {
        val photo: RoomHotel = listRoom?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listRoom?.size!!


}