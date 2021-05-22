 package haina.ecommerce.view.hotels.transactionhotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailBookingsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.transactionhotel.UnpaidItem

 class DetailBookingsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBookingsBinding
    private val helper:Helper = Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUnpaid = intent?.getParcelableExtra<UnpaidItem>("data")
        binding.toolbarDetailBooking.title = "Detail Booking"
        binding.toolbarDetailBooking.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailBooking.setNavigationOnClickListener { onBackPressed() }
        setToView(dataUnpaid!!)
    }

     private fun setToView(data:UnpaidItem){
         Glide.with(applicationContext).load(data.hotel?.hotelImage).into(binding.ivPhotoRoom)
         binding.tvPrice.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvTotalNight.text = "${data.totalNight.toString()} Night(s)"
         binding.tvHotelNameAndLocation.text = "${data.hotel?.hotelName}"
         binding.tvAddressHotel.text = data.hotel?.hotelAddress
         binding.tvStatus.text = data.status
         binding.tvTotalPriceBook.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvBookingId.text = data.orderId

     }
}