package haina.ecommerce.view.hotels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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
import haina.ecommerce.databinding.ListItemRoomHotelBinding
import haina.ecommerce.model.hotels.Facilities
import haina.ecommerce.model.hotels.ImageRoom
import haina.ecommerce.model.hotels.Reviews
import haina.ecommerce.model.hotels.RoomHotel
import haina.ecommerce.view.hotels.listphotohotels.ListPhotoHotelActivity
import haina.ecommerce.view.hotels.listreviews.BottomSheetReviewsHotel
import haina.ecommerce.view.hotels.selectdate.SelectDateHotelActivity
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment

class DetailHotelsActivity : AppCompatActivity(), View.OnClickListener, BottomSheetReviewsHotel.ItemClickListener, AdapterListRoomHotel.ItemAdapterCallback {

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
//        Picasso.get().load(listImage[position]).placeholder(R.drawable.ps5).into(imageView)
        Glide.with(applicationContext).load(listImage[position]).placeholder(R.drawable.ps5).into(imageView)
    }

    private val listHotelRoom = arrayListOf(RoomHotel("Economy",null,"Split",10,"IDR200,000"),
            RoomHotel("Business",null,"Big Size",1,"IDR450,000"),
            RoomHotel("VIP",null,"Queen",1,"IDR500,000"),
            RoomHotel("VVIP",null,"Queen",1,"IDR600,000"),
            RoomHotel("Presidential",null,"King Size",1,"IDR1,000,000"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterRoom = AdapterListRoomHotel(applicationContext, listHotelRoom, this)
        val titleToolbar = intent.getStringExtra("nameHotel")
        binding.toolbarDetailHotels.title = titleToolbar
        binding.toolbarDetailHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailHotels.setNavigationOnClickListener { onBackPressed() }
        binding.tvSeeAllReview.setOnClickListener(this)
        toggleSaveHotel()

        binding.rvFacilities.apply {
            adapter = AdapterListFacilities(applicationContext, listFacilities)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvRoomHotel.apply {
            adapter = adapterRoom
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        binding.vpImageHotel.pageCount = listImage.size
        binding.vpImageHotel.setImageListener(imagesListener)

        binding.vpImageHotel.setImageClickListener {
        val imageRoom = Intent(applicationContext, ListPhotoHotelActivity::class.java)
            startActivity(imageRoom)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_see_all_review -> {
                supportFragmentManager.let {
                    BottomSheetReviewsHotel.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
            }
        }
    }

    private fun toggleSaveHotel(){
        binding.ivSaveHotel.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                Toast.makeText(applicationContext, "Hotel Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Unsaved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(binding: ListItemRoomHotelBinding, data: RoomHotel) {
            val intent = Intent(applicationContext, SelectDateHotelActivity::class.java)
                    .putExtra("priceRoom", data.price)
                    .putExtra("typeRoom", data.typeRoom)
            startActivity(intent)
    }

    override fun onItemClick(item: String) {
        TODO("Not yet implemented")
    }


}