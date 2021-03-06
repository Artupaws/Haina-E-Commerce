package haina.ecommerce.view.hotels.listcity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListCity
import haina.ecommerce.databinding.FragmentListCityHotelBinding
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotel
import haina.ecommerce.view.hotels.HotelBaseActivity
import java.util.*
import java.util.concurrent.TimeUnit

class ListCityHotelFragment : Fragment(), ListCityHotelContract, AdapterListCity.ItemAdapterCallBack {

    private lateinit var _binding:FragmentListCityHotelBinding
    private val binding get() = _binding
    private lateinit var presenter: ListCityHotelPresenter
    private var popUpScheduleHotel:Dialog? = null
    private var totalNight: Int = 0
    private var cityId:Int = 0
    private lateinit var requestHotel: RequestBookingHotel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListCityHotelBinding.inflate(inflater, container, false)
        presenter = ListCityHotelPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getListCity()
        binding.toolbarListCity.setNavigationOnClickListener {
            (requireActivity() as HotelBaseActivity).onBackPressed()
        }
        dialogScheduleHotel()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogScheduleHotel(){
        popUpScheduleHotel = Dialog(requireActivity())
        popUpScheduleHotel?.setContentView(R.layout.popup_schedule_hotel)
        popUpScheduleHotel?.setCancelable(true)
        popUpScheduleHotel?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.transparent))
        val window:Window = popUpScheduleHotel?.window!!
        window.setGravity(Gravity.CENTER)
        val cvCheckIn = popUpScheduleHotel?.findViewById<CardView>(R.id.cv_check_in_date)
        val tvCheckIn = popUpScheduleHotel?.findViewById<TextView>(R.id.tv_check_in_date)
        val cvCheckOut = popUpScheduleHotel?.findViewById<CardView>(R.id.cv_check_out_date)
        val tvCheckOut = popUpScheduleHotel?.findViewById<TextView>(R.id.tv_check_out_date)
        val tvTotalNight = popUpScheduleHotel?.findViewById<TextView>(R.id.tv_total_night)
        val btnSave = popUpScheduleHotel?.findViewById<Button>(R.id.btn_save)
        var checkInDate:String = ""
        var checkOutDate:String = ""

        cvCheckIn?.setOnClickListener {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val now = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        builder.setTitleText(R.string.select_date_stay)
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis,tomorrow.timeInMillis))
        builder.setCalendarConstraints(limitRange().build())

        val picker = builder.build()
        picker.show(childFragmentManager, picker.toString())
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            tvCheckIn?.text = it.first?.convertLongtoTime("dd MMM")
            checkInDate = it.first?.convertLongtoTime("yyyy-MM-dd").toString()
            tvCheckOut?.text = it.second?.convertLongtoTime("dd MMM")
            checkOutDate = it.second?.convertLongtoTime("yyyy-MM-dd").toString()
            val totalDays: Long = (it.second?.minus(it.first!!)!!)
            totalNight = TimeUnit.MILLISECONDS.toDays(totalDays).toInt()
            tvTotalNight?.text = "$totalNight"
            picker.dismiss()
        }
        }

        btnSave?.setOnClickListener {
            if (!tvCheckIn?.text?.contains("-")!!){
                requestHotel = RequestBookingHotel("ID", cityId, "ID", checkInDate, checkOutDate )
                presenter.getHotelDarma(requestHotel.countryID, requestHotel.cityId, requestHotel.paxPassport,
                    requestHotel.checkIn, requestHotel.checkOut)
            } else {
                popUpScheduleHotel?.show()
            }
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

    override fun messageGetListCity(msg: String) {
        Log.d("getListCitYHotel", msg)
    }

    override fun messageGetHotelDarma(msg: String) {
        Log.d("getHotelDarma", msg)
        if (msg.contains("Success!")){
            popUpScheduleHotel?.dismiss()
        }
    }

    override fun getListCity(data: List<DataCities?>?) {
            binding.rvCityHotel.apply {
                adapter = AdapterListCity(requireActivity(), data, this@ListCityHotelFragment)
                layoutManager = GridLayoutManager(requireActivity(), 3)
            }
    }

    override fun getHotelDarma(data: DataHotelDarma?) {
        if (data != null){
            val bundle = Bundle()
            bundle.putParcelable("dataHotel", data)
            Navigation.findNavController(binding.root).navigate(R.id.action_listCityHotelFragment_to_scheduleHotelFragment, bundle)
        }
    }

    override fun onClick(view: View, idDarma: Int) {
        when(view.id){
            R.id.cv_click -> {
                cityId = idDarma
                popUpScheduleHotel?.show()
            }
        }
    }
}