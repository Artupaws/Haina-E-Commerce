package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.databinding.ListItemPhotoRoomBinding
import haina.ecommerce.databinding.ListItemTypeRoomBinding
import haina.ecommerce.model.hotels.*


class AdapterListPhotoRoom(val context: Context, private val listTypeRoom: List<RoomImageItem?>?):
    RecyclerView.Adapter<AdapterListPhotoRoom.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPhotoRoomBinding.bind(view)
        fun bind(itemHaina: RoomImageItem){
            with(binding){
                Glide.with(context).load(itemHaina.url).into(ivPhotoRoom)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPhotoRoom.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoRoomBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListPhotoRoom.Holder, position: Int) {
        val photo: RoomImageItem = listTypeRoom?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listTypeRoom?.size!!


}