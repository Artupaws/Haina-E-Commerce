package haina.ecommerce.view.flight.schedule

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterFlightDestinationCity
import haina.ecommerce.databinding.FragmentScheduleFlightBinding
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.flight.*
import haina.ecommerce.view.flight.FlightTicketActivity
import haina.ecommerce.view.flight.fragment.BottomSheetFlightFragment
import java.util.*

class ScheduleFlightFragment : Fragment(), View.OnClickListener, ScheduleContract {

    private lateinit var _binding: FragmentScheduleFlightBinding
    private val binding get() = _binding
    private var listImage = arrayOf(
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_4.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_5.jpg"
    )
    private lateinit var imagesListener: ImageListener
    private val rotatePostIconOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.rotate_icon_post
        )
    }
    private var popupDestination: Dialog? = null
    private var popupFlightClass: Dialog? = null
    private var clicked = false
    private var roundTrip = false
    private var date: String = Calendar.getInstance().get(Calendar.DATE).toString()
    private var month: String = Calendar.getInstance().get(Calendar.MONTH).toString()
    private var listDestination = listOf(
        DestinationCountry("Indonesia", 1)
    )
    private lateinit var typeDestination: String
    private var broadcaster:LocalBroadcastManager?=null
    private var flightClass:String? = null
    private lateinit var presenter: ScheduleFlightPresenter
    private var totalAdult:Int = 0
    private var totalChild:Int = 0
    private var totalBaby:Int = 0
    private var totalPassenger:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleFlightBinding.inflate(inflater, container, false)
        imagesListener = ImageListener { position, imageView ->
            Glide.with(requireActivity()).load(listImage[position]).placeholder(R.drawable.ps5).into(
                imageView
            )
        }
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.ivFlipDestination.setOnClickListener(this)
        binding.btnFindTicket.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvFinishDate.setOnClickListener(this)
        binding.tvFromDestination.setOnClickListener(this)
        binding.tvToDestination.setOnClickListener(this)
        binding.linearTotalPassenger.setOnClickListener(this)
        binding.tvFlightClass.setOnClickListener(this)
        binding.vpFlightTicket.setImageListener(imagesListener)
        binding.vpFlightTicket.pageCount = listImage.size
        binding.vpFlightTicket.setImageClickListener {
            Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
        }
        switchDestination()
        popupDialogFlightClass()
        binding.toolbarScheduleFlight.setNavigationOnClickListener {
            (activity as FlightTicketActivity).onBackPressed()
        }
        presenter = ScheduleFlightPresenter(this, requireActivity())
        presenter.getDataAirport()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_flip_destination -> {
                onAddPostClicked()
                flipDestination(clicked)
            }
            R.id.btn_find_ticket -> {
                val fromDestination = binding.tvFromDestination.text.toString()
                val toDestination = binding.tvToDestination.text.toString()
                val dateStart = binding.tvStartDate.text.toString()
                val dateFinish = binding.tvFinishDate.text.toString()
                val totalPassengerParams = binding.tvTotalPassenger.text.toString()
                val flightClass = binding.tvFlightClass.text.toString()
                when {
                    fromDestination.isNullOrEmpty() -> {
                        binding.tvFromDestination.error = "Please input from"
                    }
                    toDestination.isNullOrEmpty() -> {
                        binding.tvToDestination.error = "Please input to"
                    }
                    dateFinish.isNullOrEmpty() -> {
                        binding.tvFinishDate.error = "Please input date finish"
                    }
                    dateStart.isNullOrEmpty() -> {
                        binding.tvFromDestination.error = "Please input date start"
                    }
                    totalPassengerParams.contains(getString(R.string.input_total_passenger)) -> {
                        binding.tvTotalPassenger.error = "Please input total passenger"
                    }
                    flightClass.isNullOrEmpty() -> {
                        binding.tvFlightClass.error = "Please input flight class"
                    }
                    else -> {
                        val data = Request(dateStart, dateFinish, fromDestination, toDestination, totalPassenger,
                            totalAdult,
                            totalChild, totalBaby, null, null, null)
                        val bundle = Bundle()
                        bundle.putParcelable("data", data)
                        Navigation.findNavController(binding.btnFindTicket)
                            .navigate(
                                R.id.action_scheduleFlightFragment_to_chooseAirlinesFragment,
                                bundle
                            )
                    }
                }
            }
            R.id.tv_start_date -> {
                date = Calendar.getInstance().get(Calendar.DATE).toString()
                month = Calendar.getInstance().get(Calendar.MONTH).toString()
                setDateFlight("First")
            }
            R.id.tv_finish_date -> {
                setDateFlight("Second")
            }
            R.id.tv_from_destination -> {
                typeDestination = "From"
                popupDestination?.show()
            }
            R.id.tv_to_destination -> {
                typeDestination = "To"
                popupDestination?.show()
            }
            R.id.linear_total_passenger -> {
                childFragmentManager.let {
                    BottomSheetFlightFragment.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
            }
            R.id.tv_flight_class -> {
                popupFlightClass?.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataDestination"))
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataPassenger"))
    }

    private val mMessageReceiver:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "dataDestination" -> {
                    val dataIntent = intent.getParcelableExtra<DataAirport>("data")
                    if (typeDestination == "From") {
                        binding.tvFromDestination.text = dataIntent.iata
                        binding.tvCityNameFrom.text = dataIntent.city
                        popupDestination?.dismiss()
                    } else if (typeDestination == "To") {
                        binding.tvToDestination.text = dataIntent.iata
                        binding.tvCityNameTo.text = dataIntent.city
                        popupDestination?.dismiss()
                    }
                }
                "dataPassenger" -> {
                    val totalPassengerParams = intent.getStringExtra("total")
                    val totalAdultParams = intent.getStringExtra("totalAdult")
                    val totalChildParams = intent.getStringExtra("totalChild")
                    val totalBabyParams = intent.getStringExtra("totalBaby")
                    totalPassenger = totalPassengerParams.toInt()
                    totalAdult = totalAdultParams.toInt()
                    totalChild = totalChildParams.toInt()
                    totalBaby = totalBabyParams.toInt()
                    setDetailPassenger(totalAdultParams!!, totalChildParams, totalBabyParams, totalPassengerParams!!)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
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
        binding.tvTotalPassenger.error = null
        binding.tvTotalAdult.text = "$totalAdult Adult(s)"
        binding.tvTotalPassenger.text = "$totalPassenger consists of : "
    }

    private fun onAddPostClicked() {
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.ivFlipDestination.startAnimation(rotatePostIconOpen)
        } else {
            binding.ivFlipDestination.startAnimation(rotatePostIconOpen)
        }
    }

    private fun flipDestination(clickedParams: Boolean) {
        val from = binding.tvFromDestination.text.toString()
        val to = binding.tvToDestination.text.toString()
        val cityFrom = binding.tvCityNameFrom.text.toString()
        val cityTo = binding.tvCityNameTo.text.toString()
        if (!clickedParams) {
            binding.tvFromDestination.text = from
            binding.tvToDestination.text = to
            binding.tvCityNameFrom.text = cityFrom
            binding.tvCityNameTo.text = cityTo
        } else {
            binding.tvFromDestination.text = to
            binding.tvToDestination.text = from
            binding.tvCityNameFrom.text = cityTo
            binding.tvCityNameTo.text = cityFrom
        }
        clicked = !clicked
    }

    private fun switchDestination() {
        binding.switchDestination.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.clGoBack.visibility = View.VISIBLE
                roundTrip = true
            } else {
                binding.clGoBack.visibility = View.GONE
            }
        }
    }

    private fun setDateFlight(typeFlight: String) {
        val builder = MaterialDatePicker.Builder.datePicker()
        val now = Calendar.getInstance()
        builder.setTitleText("Select Date Flight")
        builder.setSelection(now.timeInMillis)
        builder.setCalendarConstraints(limitRangeDate().build())

        val picker = builder.build()
        picker.show(childFragmentManager, picker.toString())
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            if (typeFlight == "First") {
                binding.tvStartDate.text = it?.convertLongtoTime("yyyy-MM-dd")
                date = it?.convertLongtoTime("dd MMM").toString()
                month = it?.convertLongtoTime("dd MMM").toString()
            } else if (typeFlight == "Second") {
                binding.tvFinishDate.text = it?.convertLongtoTime("yyyy-MM-dd")
            }
            picker.dismiss()
        }
    }

    private fun limitRangeDate(): CalendarConstraints.Builder {
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogDestination(listDestinationCountry: List<DataAirport?>?) {
        popupDestination = Dialog(requireActivity())
        popupDestination?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDestination?.setCancelable(true)
        popupDestination?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDestination?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDestination?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDestination?.findViewById<RecyclerView>(R.id.rv_destination)
        val searcView = popupDestination?.findViewById<SearchView>(R.id.sv_destination)
        actionClose?.setOnClickListener { popupDestination?.dismiss() }
        rvDestination?.apply {
            adapter = AdapterFlightDestinationCity(requireActivity(), listDestinationCountry)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogFlightClass() {
        popupFlightClass = Dialog(requireActivity())
        popupFlightClass?.setContentView(R.layout.layout_select_flight_class)
        popupFlightClass?.setCancelable(true)
        popupFlightClass?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupFlightClass?.window!!
        window.setGravity(Gravity.CENTER)
        val rdGroup = popupFlightClass?.findViewById<RadioGroup>(R.id.rd_group)
        rdGroup?.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton? = popupFlightClass?.findViewById(rdGroup.checkedRadioButtonId)
                flightClass = radio?.text.toString()
                binding.tvFlightClass.text = flightClass
                popupFlightClass?.dismiss()
            }
        )
    }

    override fun messageGetAirport(msg: String) {
        Log.d("airportData", msg)
    }

    override fun getDataAirport(data: List<DataAirport?>?) {
        popupDialogDestination(data)
    }

}