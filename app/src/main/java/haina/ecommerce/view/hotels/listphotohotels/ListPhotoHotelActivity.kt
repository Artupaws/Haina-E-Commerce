package haina.ecommerce.view.hotels.listphotohotels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListPhotoTypeRoom
import haina.ecommerce.databinding.ActivityListPhotoHotelBinding
import haina.ecommerce.model.hotels.DataItem
import haina.ecommerce.model.hotels.ImageRoom
import haina.ecommerce.model.hotels.RoomHotel
import haina.ecommerce.model.hotels.RoomImageItem

class ListPhotoHotelActivity : AppCompatActivity() {

    private lateinit var binding:ActivityListPhotoHotelBinding
    private var listTypeRoom:ArrayList<RoomHotel> = ArrayList()
    private var listPhotoRoom:ArrayList<RoomImageItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPhotoHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initDataTypeRoom()
        val data = intent?.getParcelableExtra<DataItem>("dataHotel")
        binding.toolbarListPhotoHotel.title = "Detail Photo Rooms"
        binding.toolbarListPhotoHotel.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarListPhotoHotel.setNavigationOnClickListener { onBackPressed() }

        binding.rvTypeRoom.apply {
            adapter = AdapterListPhotoTypeRoom(applicationContext, listTypeRoom)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

//    fun initDataTypeRoom(){
//        listTypeRoom = ArrayList()
//        listTypeRoom.add(RoomHotel("VIP ROOM", listPhotoRoom, "", 0, ""))
//        listTypeRoom.add(RoomHotel("VVIP ROOM", listPhotoRoom, "", 0, ""))
//        listTypeRoom.add(RoomHotel("BUSINESS ROOM", listPhotoRoom, "", 0, ""))
//        listTypeRoom.add(RoomHotel("PRESIDENTIAL ROOM", listPhotoRoom, "", 0, ""))
//    }

}