package haina.ecommerce.view.hotels.listphotohotels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.hotel.AdapterListPhotoTypeRoom
import haina.ecommerce.databinding.ActivityListPhotoHotelBinding
import haina.ecommerce.model.hotels.ImageRoom
import haina.ecommerce.model.hotels.RoomHotel

class ListPhotoHotelActivity : AppCompatActivity() {

    private lateinit var binding:ActivityListPhotoHotelBinding
    private var listTypeRoom:ArrayList<RoomHotel> = ArrayList()
    private var listPhotoRoom:ArrayList<ImageRoom> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPhotoHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDataTypeRoom()
        initDataImageRoom()
        binding.rvTypeRoom.apply {
            adapter = AdapterListPhotoTypeRoom(applicationContext, listTypeRoom)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    fun initDataTypeRoom(){
        listTypeRoom = ArrayList()
        listTypeRoom.add(RoomHotel("VIP ROOM", listPhotoRoom, "", 0, ""))
        listTypeRoom.add(RoomHotel("VVIP ROOM", listPhotoRoom, "", 0, ""))
        listTypeRoom.add(RoomHotel("BUSINESS ROOM", listPhotoRoom, "", 0, ""))
        listTypeRoom.add(RoomHotel("PRESIDENTIAL ROOM", listPhotoRoom, "", 0, ""))
    }

    fun initDataImageRoom(){
        listPhotoRoom = ArrayList()
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg"))
        listPhotoRoom.add(ImageRoom("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_4.jpg"))
    }
}