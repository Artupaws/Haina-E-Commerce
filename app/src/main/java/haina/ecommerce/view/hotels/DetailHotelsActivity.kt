package haina.ecommerce.view.hotels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListFacilities
import haina.ecommerce.adapter.hotel.AdapterListRoomHotel
import haina.ecommerce.databinding.ActivityDetailHotelsBinding
import haina.ecommerce.model.hotels.Facilities
import haina.ecommerce.model.hotels.ImageRoom
import haina.ecommerce.model.hotels.RoomHotel
import haina.ecommerce.view.hotels.selectdate.SelectDateHotelActivity

class DetailHotelsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailHotelsBinding

    private val listFacilities = arrayListOf(Facilities("Television"),
    Facilities("Bathtub"),
    Facilities("Parking"),
    Facilities("Swimming Pool"),
    Facilities("Breakfast"),
    Facilities("Wifi"))

    private val listImage = arrayOf(
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_4.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_5.jpg"
    )

    private val imagesListener = ImageListener{ position, imageView ->
        Picasso.get().load(listImage[position]).placeholder(R.drawable.ps5).into(imageView)
//        Glide.with(applicationContext).load(listImage[position]).placeholder(R.drawable.ps5).into(imageView)
    }

    private val listHotelRoom = arrayListOf(RoomHotel("Economy","","Split",10,"IDR200,000"),
            RoomHotel("Business","","Big Size",1,"IDR450,000"),
            RoomHotel("VIP","","Queen",1,"IDR500,000"),
            RoomHotel("VVIP","","Queen",1,"IDR600,000"),
            RoomHotel("Presidential","","King Size",1,"IDR1,000,000"))

//    val promos = listOf(
//        ImageRoom(
//            imageRoom = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
//        ImageRoom(
//            imageRoom = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"),
//        ImageRoom(
//            imageRoom = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
//        ImageRoom(
//            imageRoom = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"),
//        ImageRoom(
//            imageRoom = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"),
//        ImageRoom(
//            imageRoom = "https://s4.bukalapak.com/uploads/promo_partnerinfo_bloggy/5042/Bloggy_1.jpg"
//        )
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterRoom = AdapterListRoomHotel(applicationContext, listHotelRoom)
        val titleToolbar = intent.getStringExtra("nameHotel")
        binding.toolbarDetailHotels.title = titleToolbar
        binding.toolbarDetailHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailHotels.setNavigationOnClickListener { onBackPressed() }

        binding.rvFacilities.apply {
            adapter = AdapterListFacilities(applicationContext, listFacilities)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvRoomHotel.apply {
            adapter = adapterRoom
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        adapterRoom.onItemClick = {priceRoom:String, typeRoom:String ->
            val intent = Intent(applicationContext, SelectDateHotelActivity::class.java)
                .putExtra("priceRoom", priceRoom)
                .putExtra("typeRoom", typeRoom)
            startActivity(intent)
        }

        binding.vpImageHotel.pageCount = listImage.size
        binding.vpImageHotel.setImageListener(imagesListener)

        binding.vpImageHotel.setImageClickListener {
            position -> Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_SHORT).show() }

    }
}