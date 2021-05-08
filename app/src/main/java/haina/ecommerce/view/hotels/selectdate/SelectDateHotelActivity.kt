package haina.ecommerce.view.hotels.selectdate

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivitySelectDateHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.hotels.DataItem
import haina.ecommerce.model.hotels.Requesthotel
import haina.ecommerce.model.hotels.RoomsItem
import haina.ecommerce.view.paymentmethod.PaymentActivity
import java.util.*
import java.util.concurrent.TimeUnit

class SelectDateHotelActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectDateHotelBinding
    val helper: Helper = Helper
    private var totalNight: Int = 0
    private var totalGuests: Int = 1
    private var maxTotalGuests:Int?=null
    private var priceRoomValue: Int? = null
    private lateinit var dataHotel:DataItem
    private lateinit var dataRoom:RoomsItem
    private lateinit var totalPrice:String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDateHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cvCheckInDate.setOnClickListener(this)
        binding.cvAddGuests.setOnClickListener(this)
        binding.cvMinusGuests.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.tvSeeOnMap.setOnClickListener(this)

        dataRoom = intent.getParcelableExtra("dataRoom")
        dataHotel = intent.getParcelableExtra("dataHotel")

        binding.toolbarSelectDate.title = "Complete Order"
        binding.toolbarSelectDate.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarSelectDate.setNavigationIcon(R.drawable.ic_back_black)
        val priceRoom = helper.convertToFormatMoneyIDRFilter(dataRoom.roomPrice.toString())
        binding.tvPriceRoom.text = priceRoom
        binding.tvNameRoom.text = dataRoom.roomName
        binding.tvMaximumGuest.text = "Maximum Guest(s) : ${dataRoom.roomMaxguest.toString()}"
        binding.tvTotalNight.text = "$totalNight"
        binding.tvTypeBed.text = dataRoom.roomBedType
        binding.tvAddressHotel.text = dataHotel.hotelAddress
        maxTotalGuests = dataRoom.roomMaxguest
        binding.etTotalGuests.setText(totalGuests.toString())
        priceRoomValue = helper.changeFormatMoneyToValueFilter(priceRoom)?.toInt()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cv_check_in_date -> {
                setDateCheckIn()
            }

            R.id.btn_next -> {
                checkDataBooking()
            }

            R.id.cv_add_guests -> {
                addGuests()
            }

            R.id.cv_minus_guests -> {
                minusGuests()
            }
            R.id.tv_see_on_map -> {
                intentToMaps(dataHotel.hotelLat!!, dataHotel.hotelLong!!)
            }
        }
    }

    private fun checkDataBooking(){
        val hotelId = dataRoom.hotelId
        val roomId = dataRoom.id
        val checkIn = binding.tvCheckInDate.text.toString()
        val checkOut = binding.tvCheckOutDate.text.toString()
        val totalGuest = totalGuests
        val totalPriceParams = totalPrice
        when{
         checkIn.isNullOrEmpty()->{
             binding.tvCheckInDate.error = "Please choose your checkin date"
         }
         checkOut.isNullOrEmpty()->{
             binding.tvCheckOutDate.error = "Please choose your checkin date"
         }  else -> {
            val dataBookingHotel = Requesthotel(hotelId, roomId, checkIn, checkOut, totalGuest, totalPriceParams)
            val intentToPayment = Intent(applicationContext, PaymentActivity::class.java)
                .putExtra("dataBooking", dataBookingHotel)
            startActivity(intentToPayment)
        }
        }
    }

    private fun addGuests() {
        if (totalGuests < maxTotalGuests!!) {
            totalGuests++
            binding.etTotalGuests.setText(totalGuests.toString())
        } else if (totalGuests == maxTotalGuests) {
            Toast.makeText(applicationContext, "Maximum total guests", Toast.LENGTH_SHORT).show()
            binding.etTotalGuests.setText(totalGuests.toString())
        }
    }

    private fun minusGuests() {
        if (totalGuests == 1) {
            totalGuests = 1
            binding.etTotalGuests.setText(totalGuests.toString())
        } else if (totalGuests >= 1) {
            totalGuests--
            binding.etTotalGuests.setText(totalGuests.toString())
        }
    }

    private fun setDateCheckIn() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val now = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        builder.setTitleText(R.string.select_date_stay)
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis,tomorrow.timeInMillis))
        builder.setCalendarConstraints(limitRange().build())

        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            binding.tvCheckInDate.text = it.first?.convertLongtoTime("dd MMM")
            binding.tvCheckOutDate.text = it.second?.convertLongtoTime("dd MMM")
            val totalDays:Long = (it.second?.minus(it.first!!)!!)
            totalNight = TimeUnit.MILLISECONDS.toDays(totalDays).toInt()
            binding.tvTotalNight.text = "$totalNight"
            picker.dismiss()
            setPriceRoom(binding.tvTotalNight.text.toString().toInt())
        }
    }

    private fun limitRange(): CalendarConstraints.Builder {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart: Calendar = Calendar.getInstance()
        val calendarEnd: Calendar = Calendar.getInstance()
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val startMonth = Calendar.getInstance().get(Calendar.MONTH)
        val startDate = Calendar.getInstance().get(Calendar.DATE)
        val endMonth = 12
        val endDate = 31

        calendarStart.set(year, startMonth, startDate-1)
        calendarEnd.set(year, endMonth - 1, endDate)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }

    private fun setPriceRoom(totalNight: Int) {
        binding.cvTotalPrice.visibility = View.VISIBLE
        totalPrice = helper.convertToFormatMoneyIDRFilter((totalNight * priceRoomValue!!).toString()).toString()
        binding.tvTotalPayment.text = totalPrice
    }

    private fun intentToMaps(lat:Double, long:Double){
        val gmmIntentUri = Uri.parse("geo:0,0?q=${lat},${long},z=5")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }
}