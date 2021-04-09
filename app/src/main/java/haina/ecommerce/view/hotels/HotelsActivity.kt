package haina.ecommerce.view.hotels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListHotel
import haina.ecommerce.adapter.hotel.AdapterListLocationHotel
import haina.ecommerce.databinding.ActivityHotelsBinding
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.model.hotels.LocationHotels

class HotelsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHotelsBinding

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

        binding.toolbarHotels.title = applicationContext.getString(R.string.hotels)
        binding.toolbarHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotels.setNavigationOnClickListener { onBackPressed() }

        binding.rvHotels.apply {
            adapter = AdapterListHotel(applicationContext, listHotel)
            layoutManager = GridLayoutManager(applicationContext, 2)
        }

        binding.rvLocationHotels.apply {
            adapter = AdapterListLocationHotel(applicationContext, listLocation)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

    }
}