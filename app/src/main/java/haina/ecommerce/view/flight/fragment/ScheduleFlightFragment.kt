package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.util.Pair
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentScheduleFlightBinding
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.helper.RangeValidator
import haina.ecommerce.model.flight.Request
import haina.ecommerce.view.flight.FlightTicketActivity
import java.time.Month
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleFlightFragment : Fragment(), View.OnClickListener {

    private lateinit var _binding:FragmentScheduleFlightBinding
    private val binding get() = _binding
    private var listImage = arrayOf("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_4.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_5.jpg")
    private lateinit var imagesListener :ImageListener
    private val rotatePostIconOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            activity,
            R.anim.rotate_icon_post
        )
    }
    private var clicked = false
    private var roundTrip = false
    private var date:String = Calendar.getInstance().get(Calendar.DATE).toString()
    private var month:String = Calendar.getInstance().get(Calendar.MONTH).toString()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentScheduleFlightBinding.inflate(inflater, container, false)
        imagesListener = ImageListener { position, imageView ->
            Glide.with(requireActivity()).load(listImage[position]).placeholder(R.drawable.ps5).into(imageView)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.ivFlipDestination.setOnClickListener(this)
        binding.btnFindTicket.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvFinishDate.setOnClickListener(this)
        binding.vpFlightTicket.setImageListener(imagesListener)
        binding.vpFlightTicket.pageCount = listImage.size
        binding.vpFlightTicket.setImageClickListener {
         Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
        }
        switchDestination()
        binding.toolbarScheduleFlight.setNavigationOnClickListener {
            (activity as FlightTicketActivity).onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_flip_destination -> {
                onAddPostClicked()
                flipDestination(clicked)
            }
            R.id.btn_find_ticket -> {
                val fromDestination = binding.tvFromDestination.text.toString()
                val toDestination = binding.tvToDestination.text.toString()
                val dateStart = binding.tvStartDate.text.toString()
                val dateFinish = binding.tvFinishDate.text.toString()
                val totalPassenger = binding.tvTotalPassenger.text.toString()
                val flightClass = binding.tvFlightClass.text.toString()
                when{
                    fromDestination.isNullOrEmpty()->{
                        binding.tvFromDestination.error = "Please input from"
                    }
                    toDestination.isNullOrEmpty()->{
                        binding.tvToDestination.error = "Please input to"
                    }
                    dateFinish.isNullOrEmpty()->{
                        binding.tvFinishDate.error = "Please input date finish"
                    }
                    dateStart.isNullOrEmpty()->{
                        binding.tvFromDestination.error = "Please input date start"
                    }
                    totalPassenger.isNullOrEmpty()->{
                        binding.tvTotalPassenger.error = "Please input total passenger"
                    }
                    flightClass.isNullOrEmpty()->{
                        binding.tvFlightClass.error = "Please input flight class"
                    } else -> {
                        val data = Request(
                            dateStart, dateFinish, fromDestination, toDestination, totalPassenger, flightClass,null,null
                        )
                    val bundle = Bundle()
                    bundle.putParcelable("data", data)
                    Navigation.findNavController(binding.btnFindTicket)
                        .navigate(R.id.action_scheduleFlightFragment_to_chooseAirlinesFragment, bundle)
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
        }
    }

    private fun onAddPostClicked(){
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked:Boolean){
        if (!clicked){
            binding.ivFlipDestination.startAnimation(rotatePostIconOpen)
        } else {
            binding.ivFlipDestination.startAnimation(rotatePostIconOpen)
        }
    }

    private fun flipDestination(clickedParams: Boolean){
        val from = binding.tvFromDestination.text.toString()
        val to = binding.tvToDestination.text.toString()
        val cityFrom = binding.tvCityNameFrom.text.toString()
        val cityTo = binding.tvCityNameTo.text.toString()
        if (!clickedParams){
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

    private fun switchDestination(){
        binding.switchDestination.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.clGoBack.visibility = View.VISIBLE
                roundTrip = true
            } else {
                binding.clGoBack.visibility = View.GONE
            }
        }
    }

    private fun setDateFlight(typeFlight:String) {
        val builder = MaterialDatePicker.Builder.datePicker()
        val now = Calendar.getInstance()
        builder.setTitleText("Select Date Flight")
        builder.setSelection(now.timeInMillis)
        builder.setCalendarConstraints(limitRangeDate().build())

        val picker = builder.build()
        picker.show(childFragmentManager, picker.toString())
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
           if (typeFlight == "First"){
               binding.tvStartDate.text = it?.convertLongtoTime("dd MMM")
               date = it?.convertLongtoTime("dd MMM").toString().substring(0,2)
               month = it?.convertLongtoTime("dd MM").toString().substring(3,5)
           } else if (typeFlight == "Second"){
               binding.tvFinishDate.text = it?.convertLongtoTime("dd MMM")
           }
            picker.dismiss()
        }
    }

    private fun limitRangeDate(): CalendarConstraints.Builder {
        val constraintsBuilderRange = CalendarConstraints.Builder()
        val calendarStart: Calendar = Calendar.getInstance()
        val calendarEnd: Calendar = Calendar.getInstance()
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val startMonth = month.toInt()
        val startDate = date.toInt()
        val endMonth = 12
        val endDate = 31

        calendarStart.set(year, startMonth-1, startDate-1)
        calendarEnd.set(year, endMonth - 1, endDate)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)
        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
        return constraintsBuilderRange
    }

}