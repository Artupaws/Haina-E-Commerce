package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemTypeRoomBinding
import haina.ecommerce.model.hotels.*


class AdapterListPhotoTypeRoom(val context: Context, private val listTypeRoom: List<RoomsItem?>?):
    RecyclerView.Adapter<AdapterListPhotoTypeRoom.Holder>() {


    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemTypeRoomBinding.bind(view)
        fun bind(itemHaina: RoomsItem){
            with(binding){
                tvTypeRoom.text = itemHaina.roomName
                rvPhotoRoom.apply {
                    adapter = AdapterListPhotoRoom(context, itemHaina.roomImage)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPhotoTypeRoom.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTypeRoomBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListPhotoTypeRoom.Holder, position: Int) {
        val photo: RoomsItem = listTypeRoom?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listTypeRoom?.size!!

}