 package haina.ecommerce.view.hotels.transactionhotel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailBookingsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.newHotel.PaidItem
import haina.ecommerce.model.hotels.transactionhotel.UnpaidItem

 class DetailBookingsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityDetailBookingsBinding
    private val helper:Helper = Helper
     private var address:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUnpaid = intent?.getParcelableExtra<PaidItem>("data")
        binding.toolbarDetailBooking.title = "Detail Booking"
        binding.toolbarDetailBooking.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailBooking.setNavigationOnClickListener { onBackPressed() }
        binding.tvLocationHotel.setOnClickListener(this)
        address = dataUnpaid?.hotel?.hotelAddress.toString()
        setToView(dataUnpaid!!)
    }

     private fun setToView(data:PaidItem){
//         Glide.with(applicationContext).load(data.hotel?.hotelImage).into(binding.ivPhotoRoom)
         binding.tvPrice.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvTotalNight.text = "${data.totalNight.toString()} Night(s)"
         binding.tvHotelNameAndLocation.text = "${data.hotel?.hotelName}"
         binding.tvAddressHotel.text = data.hotel?.hotelAddress
         binding.tvStatus.text = data.status
         binding.tvRoomType.text = data.room?.roomName
         binding.tvCheckInDate.text = helper.formatDate(data.checkIn!!)
         binding.tvCheckOutDate.text = helper.formatDate(data.checkOut!!)
         binding.tvTotalPriceBook.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvBookingId.text = data.payment?.bookingId.toString()
         val paymentMethod = "${data.paymentMethod?.name}-${data.paymentMethod?.category?.name}"
         binding.tvPaymentMethod.text = paymentMethod
         val breakfast = if (data.breakfast != "Room Only") {
             getString(R.string.breakfast_status_no)
         } else {
             getString(R.string.breakfast_status_yes)
         }
         binding.tvBreakfast.text = breakfast
         binding.tvTotalGuest.text = data.totalGuest.toString()
     }

     private fun intentToMaps(address:String){
         val gmmIntentUri = Uri.parse("geo:0,0?q=$address,z=14")
         val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
         mapIntent.setPackage("com.google.android.apps.maps")
         mapIntent.resolveActivity(packageManager)?.let {
             startActivity(mapIntent)
         }
     }

     override fun onClick(p0: View?) {
         when(p0?.id){
             R.id.tv_location_hotel -> {
                 intentToMaps(address)
             }
         }
     }
 }