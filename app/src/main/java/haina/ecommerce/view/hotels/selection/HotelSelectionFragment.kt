package haina.ecommerce.view.hotels.selection

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListCity
import haina.ecommerce.databinding.FragmentHotelSelectionBinding
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.flight.DataAirport
import haina.ecommerce.model.hotels.HotelSearchItem
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotel
import haina.ecommerce.view.flight.fragment.BottomSheetFlightFragment
import haina.ecommerce.view.hotels.HotelBaseActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HotelSelectionFragment : Fragment(), HotelSelectionContract.View, AdapterListCity.ItemAdapterCallBack {

    private lateinit var _binding: FragmentHotelSelectionBinding
    private val binding get() = _binding
    private lateinit var selectionPresenter: HotelSelectionPresenter
    private var popUpScheduleHotel: Dialog? = null
    private var progressDialog: Dialog? = null
    private var totalNight: Int = 0
    private var cityId: Int = 0
    private lateinit var requestHotel: RequestBookingHotel
    private var unfinishBookingSize: Int = 0

    private var totalAdult:Int = 0
    private var totalChild:Int = 0
    private var totalBaby:Int = 0
    private var totalPassenger:Int = 0
    var bottomsheet:BottomSheetSearchHotelFragment? = null

    var checkInDate: String = ""
    var checkOutDate: String = ""
    var searchType:String? = null
    var searchid:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelSelectionBinding.inflate(inflater, container, false)
        selectionPresenter = HotelSelectionPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogLoading()
        selectionPresenter.getListCity()
        selectionPresenter.getListTransactionHotelDarma()
        binding.toolbarHotelSelection.setNavigationOnClickListener {
            (requireActivity() as HotelBaseActivity).onBackPressed()
        }
        checkInDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        var dt=Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt=c.time
        checkOutDate = SimpleDateFormat("yyyy-MM-dd").format(dt)
        binding.tvStartDate?.text =
            SimpleDateFormat("dd MMM").format(Date()) + " - " + SimpleDateFormat("dd MMM").format(dt)
        binding.tvTotalNight?.text = "1 Night(s)"


        selectionPresenter.getSearchHotel("Jakarta",checkInDate,checkOutDate)
        binding.tvStartDate.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val now = Calendar.getInstance()
            val tomorrow = Calendar.getInstance()
            tomorrow.add(Calendar.DAY_OF_MONTH, 1)
            builder.setTitleText(R.string.select_date_stay)
            builder.setSelection(androidx.core.util.Pair(now.timeInMillis, tomorrow.timeInMillis))
            builder.setCalendarConstraints(limitRange().build())

            val picker = builder.build()
            picker.show(childFragmentManager, picker.toString())
            picker.addOnNegativeButtonClickListener { picker.dismiss() }
            picker.addOnPositiveButtonClickListener {
                binding.tvStartDate?.text =
                    it.first?.convertLongtoTime("dd MMM") + " - " + it.second?.convertLongtoTime("dd MMM")
                checkInDate = it.first?.convertLongtoTime("yyyy-MM-dd").toString()
                checkOutDate = it.second?.convertLongtoTime("yyyy-MM-dd").toString()
                val totalDays: Long = (it.second?.minus(it.first!!)!!)
                totalNight = TimeUnit.MILLISECONDS.toDays(totalDays).toInt()

                binding.tvTotalNight?.text = "$totalNight Night(s)"
                picker.dismiss()
            }
        }
        binding.clPax.setOnClickListener {
            childFragmentManager.let {
                var bundle:Bundle= Bundle()

                BottomSheetHotelFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
//        binding.svDestination.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
//            SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                if (p0?.isNotEmpty()!!){
//                    (binding.rvCityHotel.adapter as AdapterListCity).filter.filter(p0)
//                    (binding.rvCityHotel.adapter as AdapterListCity).notifyDataSetChanged()
//                }
//                return true
//            }
//        })
    }

    private fun dialogLoading() {
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                android.R.color.white
            )
        )
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
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

        calendarStart.set(year, startMonth, startDate - 1)
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
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Success!")) {
            popUpScheduleHotel?.dismiss()
        }
    }

    override fun messageGetListTransactionHotel(msg: String) {
        Log.d("getListUnfinish", msg)
    }

    override fun getListCity(data: List<DataCities?>?) {
        progressDialog?.dismiss()
//            binding.rvCityHotel.apply {
//                adapter = AdapterListCity(requireActivity(), data, this@HotelSelectionFragment)
//                layoutManager = GridLayoutManager(requireActivity(), 3)
//            }
    }

    override fun getHotelDarma(data: DataHotelDarma?) {
        if (data != null) {
            val bundle = Bundle()
            bundle.putParcelable("dataHotel", data)
            bundle.putInt("totalNight", totalNight)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_listCityHotelFragment_to_scheduleHotelFragment, bundle)
        }
    }

    override fun getSizeListUnfinish(size: Int?) {
        if (size != null) {
            unfinishBookingSize = size
        }
    }

    override fun getSearch(data: List<HotelSearchItem?>) {
        var bundle:Bundle=Bundle()
        bundle.putParcelableArrayList("data",data as java.util.ArrayList)
        bundle.putString("checkindate",checkInDate)
        bundle.putString("checkoutdate",checkOutDate)

        bottomsheet=BottomSheetSearchHotelFragment()

        bottomsheet?.arguments=bundle

        binding.tvHotelName.setOnClickListener{
            childFragmentManager.let {
                bottomsheet?.apply {
                    show(it, tag)
                }
            }
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(view: View, idDarma: Int) {
        when (view.id) {
            R.id.cv_click -> {
                cityId = idDarma
                if (unfinishBookingSize == 0) {
                    popUpScheduleHotel?.show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.warning_booking_hotel),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var intent:IntentFilter= IntentFilter()
        intent.addAction("dataPassenger")
        intent.addAction("dataSearch")

        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(
                mMessageReceiver,intent
            )

    }
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "dataPassenger" -> {
                    val totalPassengerParams = intent.getStringExtra("total")
                    val totalAdultParams = intent.getStringExtra("totalAdult")
                    val totalChildParams = intent.getStringExtra("totalChild")
                    val totalBabyParams = intent.getStringExtra("totalBaby")
                    totalPassenger = totalPassengerParams!!.toInt()
                    totalAdult = totalAdultParams!!.toInt()
                    totalChild = totalChildParams!!.toInt()
                    totalBaby = totalBabyParams!!.toInt()
                    setDetailPassenger(totalAdultParams!!, totalChildParams, totalBabyParams, totalPassengerParams!!)
                }
                "dataSearch" -> {
                    bottomsheet?.dismiss()
                    val dataSearch = intent.getParcelableExtra<HotelSearchItem>("data")

                    Timber.d(dataSearch?.name)
                    setSearchData(dataSearch!!)
                }
            }
        }
    }

    private fun setSearchData(dataSearch:HotelSearchItem){
        binding.tvHotelName.text=dataSearch.name
        searchType=dataSearch.type
        searchid=dataSearch.iD

    }

    private fun setDetailPassenger(totalAdult:String, totalChildParams:String?, totalBabyParams:String?, totalPassenger:String){
        if (totalChildParams != "0"){
            binding.tvTotalChild.visibility = View.VISIBLE
            binding.tvTotalChild.text = "$totalChild Child(s)"
        } else {
            binding.tvTotalChild.visibility = View.GONE
            totalChild = 0
        }
        if (totalBabyParams != "0"){
            binding.tvTotalBaby.visibility = View.VISIBLE
            binding.tvTotalBaby.text = "$totalBaby Baby(s)"
        } else {
            binding.tvTotalBaby.visibility = View.GONE
            totalBaby = 0
        }
        binding.tvTotalPax.error = null
        binding.tvTotalAdult.text = "$totalAdult Adult(s)"
        binding.tvTotalPax.text = "$totalPassenger consists of : "
    }

}