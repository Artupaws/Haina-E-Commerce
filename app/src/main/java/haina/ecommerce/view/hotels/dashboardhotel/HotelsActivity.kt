package haina.ecommerce.view.hotels.dashboardhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListHotel
import haina.ecommerce.adapter.hotel.AdapterListLocationHotel
import haina.ecommerce.databinding.ActivityHotelsBinding
import haina.ecommerce.model.hotels.DataItem
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.model.hotels.LocationHotels
import haina.ecommerce.view.hotels.detailhotel.DetailHotelsActivity

class HotelsActivity : AppCompatActivity(), HotelContract, AdapterListHotel.ItemAdapterCallBack {

    private lateinit var binding:ActivityHotelsBinding
    private lateinit var presenter: HotelPresenter

    private val listHotel = arrayListOf(
        Hotels("Borobudur", "Jakarta Pusat", "IDR400,000"),
        Hotels("Prambanan", "Jakarta Utara", "IDR400,000"),
        Hotels("Grand Indonesia", "Jakarta Selatan", "IDR400,000"),
        Hotels("Holiday Inn", "Jakarta Timur", "IDR400,000"),
        Hotels("Holiday Inn", "Jakarta Timur", "IDR400,000")
    )

    private val listLocation = arrayListOf(
        LocationHotels("Jakarta"),
        LocationHotels("Bandung"),
        LocationHotels("Yogyakarta"),
        LocationHotels("Surabaya")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HotelPresenter(this)
        presenter.getAllHotel()
        binding.toolbarHotels.title = applicationContext.getString(R.string.hotels)
        binding.toolbarHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotels.setNavigationOnClickListener { onBackPressed() }

//        binding.rvHotels.apply {
//            adapter = AdapterListHotel(applicationContext, listHotel)
//            layoutManager = GridLayoutManager(applicationContext, 2)
//        }

        binding.rvLocationHotels.apply {
            adapter = AdapterListLocationHotel(applicationContext, listLocation)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun getMessageHotel(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getDataAllHotel(data: List<DataItem?>?) {
        binding.rvHotels.apply {
            adapter = AdapterListHotel(applicationContext, data, this@HotelsActivity)
            layoutManager = GridLayoutManager(applicationContext, 2)
        }
    }

    override fun onClick(view: View, dataHotel: DataItem) {
        when(view.id){
            R.id.cv_click -> {
                val intent = Intent(this, DetailHotelsActivity::class.java)
                        .putExtra("dataHotel", dataHotel)
                startActivity(intent)
            }
        }
    }
}