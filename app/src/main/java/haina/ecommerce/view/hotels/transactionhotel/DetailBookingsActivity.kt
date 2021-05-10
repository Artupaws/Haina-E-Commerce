 package haina.ecommerce.view.hotels.transactionhotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailBookingsBinding

class DetailBookingsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBookingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetailBooking.title = "Detail Booking"
        binding.toolbarDetailBooking.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailBooking.setNavigationOnClickListener { onBackPressed() }

    }
}