package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAirlines
import haina.ecommerce.databinding.FragmentChooseAirlinesSecondFlightBinding
import haina.ecommerce.model.flight.*

class ChooseAirlinesSecondFlightFragment : Fragment(), AdapterAirlines.ItemAdapterCallback {

    private lateinit var _binding: FragmentChooseAirlinesSecondFlightBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private val listTimeFlight = listOf<TimeFlight>(
            TimeFlight("05:55", "06:55", "", "")
    )
    private val listAirlines = arrayListOf<Airlines>(
            Airlines("Garuda", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Lion", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Batavia", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Wings", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Adam", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Mandala", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
            Airlines("Buroq", "", listTimeFlight, "1h0m", "Direct", "CGK", "JOG", "500000", "05:55", "06:55"),
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChooseAirlinesSecondFlightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable<Request>("data")!!
        setDataFirstFlight(data.airlinesFirst!!)
        binding.toolbarChooseAirlines.title = "${data.toDestination} - ${data.fromDestination}"
        binding.toolbarChooseAirlines.subtitle = "${data.finishDate} | ${data.totalPassenger}"
        binding.toolbarChooseAirlines.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

//        binding.rvAirlines.apply {
//            adapter = AdapterAirlines(requireActivity(), listAirlines, this@ChooseAirlinesSecondFlightFragment)
//            layoutManager =
//                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        }
    }

    private fun setDataFirstFlight(dataAirlinesFirst:AirlinesFirst){
        val codeCityAndAirlineName = "${dataAirlinesFirst.cityCodeDeparture} - ${dataAirlinesFirst.cityCodeArrived} | ${dataAirlinesFirst.nameAirlines}"
        val dateAndTimeFlight = "${data.startDate} | ${dataAirlinesFirst.departureTime} - ${dataAirlinesFirst.arrivedTime}"
        binding.includeFirstFlight.tvCodecityAndAirlineName.text = codeCityAndAirlineName
        binding.includeFirstFlight.tvDateFlightAndTime.text = dateAndTimeFlight
        
    }

    override fun onClick(view: View, data: DepartItem, timeFlight: List<TimeFlight>) {
//        val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination, this.data.totalPassenger,
//            this.data.flightClass, this.data.airlinesFirst, airlinesSecond = AirlinesSecond(data.nameAirlines,
//                data.iconAirline, data.listFlightTime, data.flightTime, data.typeFlight,
//                data.cityCodeDeparture,data.cityCodeArrived, data.priceTicket, data.departureTime,
//                data.arrivedTime), null)
//        val bundle = Bundle()
//        bundle.putParcelable("data", dataFlight)
//        Navigation.findNavController(view).navigate(R.id.action_chooseAirlinesSecondFlightFragment_to_fillDataPassengerFragment, bundle)
    }

}