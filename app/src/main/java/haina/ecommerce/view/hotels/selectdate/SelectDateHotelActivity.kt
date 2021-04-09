package haina.ecommerce.view.hotels.selectdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivitySelectDateHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoTime
import java.util.*

class SelectDateHotelActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivitySelectDateHotelBinding
    val helper:Helper = Helper
    private var totalNight :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDateHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cvCheckInDate.setOnClickListener(this)

        val priceRoom = intent.getStringExtra("priceRoom")
        val typeRoom = intent.getStringExtra("typeRoom")
        totalNight = "0 ${getString(R.string.night)}"

        binding.toolbarSelectDate.title = "Complete Order"
        binding.toolbarSelectDate.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarSelectDate.setNavigationIcon(R.drawable.ic_back_black)
        binding.tvPriceRoom.text = priceRoom
        binding.tvNameRoom.text = typeRoom
        binding.tvTotalNight.text = totalNight


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cv_check_in_date -> {
                setDateCheckIn()
            }
        }
    }

    private fun setDateCheckIn(){
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val now = Calendar.getInstance()
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))

        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            binding.tvCheckInDate.text = it.first?.convertLongtoTime("dd MMM")
            binding.tvCheckOutDate.text = it.second?.convertLongtoTime("dd MMM")
            val checkindate = it.first?.convertLongtoTime("dd MMM")?.replace(
                it.first?.convertLongtoTime("dd MMM")!!,
                it.first?.convertLongtoTime("dd MMM")!!.substring(1,2))
            val checkoutdate = it.second?.convertLongtoTime("dd MMM")?.replace(
                it.second?.convertLongtoTime("dd MMM")!!,
                it.second?.convertLongtoTime("dd MMM")!!.substring(1,2))
            totalNight = "${checkoutdate?.toInt()?.minus(checkindate?.toInt()!!)} ${getString(R.string.night)}"
            binding.tvTotalNight.text = totalNight
            picker.dismiss()}
    }

}