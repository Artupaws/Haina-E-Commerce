 package haina.ecommerce.view.hotels.transactionhotel.detailbook

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailBookingsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.newHotel.PaidItem
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity

 class DetailBookingsActivity : AppCompatActivity(), View.OnClickListener, DetailBookContract.View {

    private lateinit var binding:ActivityDetailBookingsBinding
    private val helper:Helper = Helper
     private var address:String = ""
     private var progressDialog:Dialog? = null
     private var dialogCancel:Dialog? = null
     private lateinit var presenter: DetailBookPresenter
     private var idBookingHotel:Int = 0

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DetailBookPresenter(this, this)

        val dataUnpaid = intent?.getParcelableExtra<PaidItem>("data")
        binding.toolbarDetailBooking.title = getString(R.string.detail_booking)
        binding.toolbarDetailBooking.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDetailBooking.setNavigationOnClickListener { onBackPressed() }
        binding.tvLocationHotel.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        idBookingHotel = dataUnpaid?.id!!
        address = dataUnpaid.hotel?.hotelAddress.toString()
        setToView(dataUnpaid)
        dialogLoading()
        showPopupLogout()
    }

     private fun setToView(data:PaidItem){
//         Glide.with(applicationContext).load(data.hotel?.hotelImage).into(binding.ivPhotoRoom)
         binding.tvPrice.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvTotalNight.text = "${data.totalNight.toString()} ${getString(R.string.nights_counter)}"
         binding.tvHotelNameAndLocation.text = "${data.hotel?.hotelName}"
         binding.tvAddressHotel.text = data.hotel?.hotelAddress
         binding.tvStatus.text = data.status
         binding.tvRoomType.text = data.room?.roomName
         binding.tvCheckInDate.text = helper.formatDate(data.checkIn!!)
         binding.tvCheckOutDate.text = helper.formatDate(data.checkOut!!)
         binding.tvTotalPriceBook.text = helper.convertToFormatMoneyIDRFilter(data.totalPrice.toString())
         binding.tvBookingId.text = data.payment?.bookingId.toString()
         binding.tvCancelPolicy.text = data.cancelationPolicy
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

     @SuppressLint("UseCompatLoadingForDrawables")
     private fun dialogLoading(){
         progressDialog = Dialog(this)
         progressDialog?.setContentView(R.layout.dialog_loader)
         progressDialog?.setCancelable(false)
         progressDialog?.window?.setBackgroundDrawable(getDrawable(android.R.color.white))
         val window: Window = progressDialog?.window!!
         window.setGravity(Gravity.CENTER)
     }

     @SuppressLint("InflateParams", "UseCompatLoadingForDrawables")
     private fun showPopupLogout() {
         dialogCancel = Dialog(this)
         dialogCancel?.setCancelable(true)
         dialogCancel?.setContentView(R.layout.popup_logout)
         dialogCancel?.window?.setBackgroundDrawable(getDrawable(android.R.color.white))
         val window: Window = dialogCancel?.window!!
         window.setGravity(Gravity.CENTER)
         val actionCancel = dialogCancel?.findViewById<TextView>(R.id.tv_action_cancel)
         val actionYes = dialogCancel?.findViewById<TextView>(R.id.tv_action_yes)
         val title = dialogCancel?.findViewById<TextView>(R.id.tv_title)
         val message = dialogCancel?.findViewById<TextView>(R.id.tv_popup)
         message?.text = getString(R.string.message_cancel_hotel)
         title?.text = getString(R.string.cancel)
         actionCancel?.setOnClickListener { dialogCancel?.dismiss() }
         actionYes?.setOnClickListener {
             presenter.cancelBookingHotel(idBookingHotel)
         }
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
             R.id.btn_cancel -> {
                 dialogCancel?.show()
             }
         }
     }

     override fun messageCancelBookingHotel(msg: String) {
         Log.d("cancelBooking", msg)
         if (msg == "Booking cancelled!"){
             val intent = Intent(applicationContext, HistoryTransactionHotelActivity::class.java)
             startActivity(intent)
             finishAffinity()
         }
     }

     override fun showLoading() {
         progressDialog?.show()
     }

     override fun dismissLoading() {
         progressDialog?.dismiss()
     }
 }