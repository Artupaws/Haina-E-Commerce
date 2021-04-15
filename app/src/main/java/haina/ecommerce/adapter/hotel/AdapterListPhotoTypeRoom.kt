package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemTypeRoomBinding
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.model.hotels.ImageRoom
import haina.ecommerce.model.hotels.RoomHotel


class AdapterListPhotoTypeRoom(val context: Context, private val listTypeRoom: List<RoomHotel?>?):
    RecyclerView.Adapter<AdapterListPhotoTypeRoom.Holder>() {

    private var listPhotoRoom : ArrayList<ImageRoom> = ArrayList()

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemTypeRoomBinding.bind(view)
        fun bind(itemHaina: RoomHotel){
            with(binding){
                initDataImageRoom()
                tvTypeRoom.text = itemHaina.typeRoom
                rvPhotoRoom.apply {
                    adapter = AdapterListPhotoRoom(context, listPhotoRoom)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPhotoTypeRoom.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTypeRoomBinding.inflate(inflater)
        initDataImageRoom()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListPhotoTypeRoom.Holder, position: Int) {
        val photo: RoomHotel = listTypeRoom?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listTypeRoom?.size!!

    fun initDataImageRoom(){
        listPhotoRoom = ArrayList()
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_4.jpg"))
    }
}