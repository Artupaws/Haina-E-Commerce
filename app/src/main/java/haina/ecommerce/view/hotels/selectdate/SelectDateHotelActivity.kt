package haina.ecommerce.view.hotels.selectdate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivitySelectDateHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.view.paymentmethod.PaymentActivity
import java.util.*
import java.util.concurrent.TimeUnit

class SelectDateHotelActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectDateHotelBinding
    val helper: Helper = Helper
    private var totalNight: Int = 0
    private var totalGuests: Int = 1
    private var maxTotalGuests: Int? = 3
    private var priceRoomValue: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDateHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cvCheckInDate.setOnClickListener(this)
        binding.cvAddGuests.setOnClickListener(this)
        binding.cvMinusGuests.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        val priceRoom = intent.getStringExtra("priceRoom")
        val typeRoom = intent.getStringExtra("typeRoom")
        val totalNightDefault = "$totalNight ${getString(R.string.night)}"

        binding.toolbarSelectDate.title = "Complete Order"
        binding.toolbarSelectDate.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarSelectDate.setNavigationIcon(R.drawable.ic_back_black)
        binding.tvPriceRoom.text = priceRoom
        binding.tvNameRoom.text = typeRoom
        binding.tvTotalNight.text = "$totalNight"
        binding.etTotalGuests.setText(totalGuests.toString())
        priceRoomValue = helper.changeFormatMoneyToValueFilter(priceRoom)?.toInt()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cv_check_in_date -> {
                setDateCheckIn()
            }

            R.id.btn_next -> {
                val payment = Intent(applicationContext, PaymentActivity::class.java)
                        .putExtra("totalPrice","${binding.tvTotalPayment.text}")
                startActivity(payment)
            }

            R.id.cv_add_guests -> {
                addGuests()
            }

            R.id.cv_minus_guests -> {
                minusGuests()
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
        val totalPayment = helper.convertToFormatMoneyIDRFilter((totalNight * priceRoomValue!!).toString())
        binding.tvTotalPayment.text = totalPayment
    }
}