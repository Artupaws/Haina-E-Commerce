package haina.ecommerce.view.hotels.detailhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListFacilities
import haina.ecommerce.adapter.hotel.AdapterListRoomHotel
import haina.ecommerce.databinding.ActivityDetailHotelsBinding
import haina.ecommerce.databinding.ListItemRoomHotelBinding
import haina.ecommerce.model.hotels.*
import haina.ecommerce.view.hotels.listphotohotels.ListPhotoHotelActivity
import haina.ecommerce.view.hotels.listreviews.BottomSheetReviewsHotel
import haina.ecommerce.view.hotels.selectdate.SelectDateHotelActivity
import java.util.*
import kotlin.collections.ArrayList

class DetailHotelsActivity : AppCompatActivity(), View.OnClickListener,
    BottomSheetReviewsHotel.ItemClickListener, AdapterListRoomHotel.ItemAdapterCallback {

    private lateinit var binding: ActivityDetailHotelsBinding
    private lateinit var dataHotel: DataHotel
    private lateinit var listParams: ArrayList<String>
    private var  imagesListener : ImageListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataHotel = intent.getParcelableExtra<DataHotel>("dataHotel")!!
        binding.toolbarDetailHotels.title = getString(R.string.hotel_info)
        binding.toolbarDetailHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailHotels.setNavigationOnClickListener { onBackPressed() }
        binding.tvSeeAllReview.setOnClickListener(this)
        toggleSaveHotel()
        setupDetailHotel(dataHotel)

        binding.rvFacilities.apply {
            adapter = AdapterListFacilities(applicationContext, dataHotel.facilities)
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvRoomHotel.apply {
            adapter =
                AdapterListRoomHotel(applicationContext, dataHotel.rooms, this@DetailHotelsActivity)
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        listParams = ArrayList()
        for (i in dataHotel.rooms!!) {
            i?.roomImage?.forEach { listParams.add(it?.url!!) }
            Log.d("urlImageHotel", listParams.toString())
        }
        binding.vpImageHotel.pageCount = listParams.size
        imagesListener = ImageListener { position, imageView ->
                    Glide.with(applicationContext).load(listParams[position]).into(imageView)
                }

        binding.vpImageHotel.setImageListener(imagesListener)

        binding.vpImageHotel.setImageListener(imagesListener)
        binding.vpImageHotel.setImageClickListener {
            val imageRoom = Intent(applicationContext, ListPhotoHotelActivity::class.java)
                .putExtra("dataHotel", dataHotel)
            startActivity(imageRoom)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_see_all_review -> {
                supportFragmentManager.let {
                    BottomSheetReviewsHotel.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
            }
        }
    }

    private fun setupDetailHotel(data: DataHotel) {
        binding.tvNameHotel.text = data.hotelName
        binding.tvLocationHotel.text = data.hotelAddress
    }

    private fun toggleSaveHotel() {
        binding.ivSaveHotel.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Hotel Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Unsaved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(binding: ListItemRoomHotelBinding, data: RoomsItem) {
        val intent = Intent(applicationContext, SelectDateHotelActivity::class.java)
            .putExtra("dataRoom", data)
            .putExtra("dataHotel", dataHotel)
        startActivity(intent)
    }

    override fun onItemClick(item: String) {
        TODO("Not yet implemented")
    }


}