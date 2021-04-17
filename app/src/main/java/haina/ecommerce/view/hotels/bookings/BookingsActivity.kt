package haina.ecommerce.view.hotels.bookings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListBooking
import haina.ecommerce.databinding.ActivityBookingsBinding
import haina.ecommerce.model.hotels.Bookings

class BookingsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBookingsBinding
    private var listBooking:ArrayList<Bookings> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDataDummy()

        binding.toolbarBookingHotel.title = "Bookings"
        binding.toolbarBookingHotel.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarBookingHotel.setNavigationOnClickListener { onBackPressed() }

        binding.rvBooking.apply {
            adapter = AdapterListBooking(applicationContext, listBooking)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initDataDummy(){
        listBooking = ArrayList()
        listBooking.add(Bookings("123A","Complete","12 APR","13 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "Bagus, pelayanan ramah", 5F))
        listBooking.add(Bookings("123B","Complete","14 APR","15 APR","2", "Jakarta", "1","VIP Room","Prambanan Hotel", "Bagus, pelayanan ramah", 5f))
        listBooking.add(Bookings("123C","Complete","16 APR","17 APR","2", "Jakarta", "1","VIP Room","Sriwijaya Hotel", "Hotelnya bagus dan nyaman", 4.5f))
        listBooking.add(Bookings("123D","Complete","18 APR","19 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
        listBooking.add(Bookings("123E","Waiting For You","20 APR","21 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
    }
}