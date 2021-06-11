package haina.ecommerce.view.flight.chooseairline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.flight.AdapterAirlines
import haina.ecommerce.databinding.FragmentChooseAirlinesBinding
import haina.ecommerce.model.flight.*

class ChooseAirlinesFragment : Fragment(), AdapterAirlines.ItemAdapterCallback, ChooseAirlineFristContract {

    private lateinit var _binding: FragmentChooseAirlinesBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private val listTimeFlight = listOf<TimeFlight>(
        TimeFlight("05:55", "06:55")
    )
    private var tripType:String = ""
    private val listAirlines = arrayListOf<Airlines>(
            Airlines("Garuda", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Lion", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Batavia", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Wings", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Adam", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Mandala", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Buroq", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
    )
    private lateinit var presenter: ChooseAirlineFirstPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChooseAirlinesBinding.inflate(inflater, container, false)
        presenter = ChooseAirlineFirstPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable("data")!!
        tripType = if (data.finishDate!!.contains("select date")){
            "OneWay"
        } else {
            "RoundTrip"
        }
        presenter.getAirlinesData(tripType, data.fromDestination, data.toDestination, data.startDate, data.finishDate, data.totalAdult, data.totalChild,
            data.totalBaby, "1")
        binding.toolbarChooseAirlines.title = "${data.fromDestination} - ${data.toDestination}"
        binding.toolbarChooseAirlines.subtitle = "${data.startDate} | ${data.totalPassenger}"
        binding.toolbarChooseAirlines.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

//        binding.rvAirlines.apply {
//            adapter = AdapterAirlines(requireActivity(), listAirlines, this@ChooseAirlinesFragment)
//            layoutManager =
//                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        }
    }

//    override fun onClick(view:View, data:Airlines) {
//        val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination, this.data.totalPassenger,
//            this.data.flightClass, airlinesFirst = AirlinesFirst(data.nameAirlines,
//                data.iconAirline, data.listFlightTime, data.flightTime, data.typeFlight,
//                data.cityCodeDeparture,data.cityCodeArrived, data.priceTicket, data.departureTime,
//                data.arrivedTime), null, null)
//        val bundle = Bundle()
//        bundle.putParcelable("data", dataFlight)
//        if (this.data.finishDate?.contains("select date")!!){
//            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_fillDataPassengerFragment, bundle)
//        } else if (!this.data.finishDate?.contains("select date")!!){
//            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_chooseAirlinesSecondFlightFragment, bundle)
//        }
//    }

    override fun messageChooseAirline(msg: String) {
        Log.d("getAirlines", msg)
    }

    override fun getDataAirline(data: DataAirline?) {
        binding.rvAirlines.apply {
            adapter = AdapterAirlines(requireActivity(), data?.depart, this@ChooseAirlinesFragment)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(view: View, dataDepart: DepartItem, timeFlight: List<TimeFlight>) {
        val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination,
        this.data.totalPassenger, this.data.totalAdult, this.data.totalChild, this.data.totalBaby, airlinesFirst = AirlinesFirst(dataDepart.airlineCode!!,
                "", timeFlight, dataDepart.departTime!!, "Direct Flight",
                dataDepart.origin!!,dataDepart.destination!!, dataDepart.price.toString(), dataDepart.departTime.toString(),
                dataDepart.arrivalTime!!), null, null)
        val bundle = Bundle()
        bundle.putParcelable("data", dataFlight)
        if (this.data.finishDate?.contains("select date")!!){
            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_fillDataPassengerFragment, bundle)
        } else if (!this.data.finishDate?.contains("select date")!!){
            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_chooseAirlinesSecondFlightFragment, bundle)
        }
    }

}