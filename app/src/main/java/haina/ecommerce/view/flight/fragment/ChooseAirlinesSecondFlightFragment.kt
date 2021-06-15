package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AdapterAirlines
import haina.ecommerce.databinding.FragmentChooseAirlinesSecondFlightBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.*
import haina.ecommerce.view.flight.chooseairline.ChooseAirlineFristContract
import java.util.ArrayList

class ChooseAirlinesSecondFlightFragment : Fragment(), AdapterAirlines.ItemAdapterCallback, ChooseAirlineFristContract {

    private lateinit var _binding: FragmentChooseAirlinesSecondFlightBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private val helper:Helper = Helper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChooseAirlinesSecondFlightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable<Request>("data")!!
//        setDataFirstFlight(data.airlinesFirst!!)
        binding.toolbarChooseAirlines.title = "${data.toDestination} - ${data.fromDestination}"
        binding.toolbarChooseAirlines.subtitle = "${data.finishDate} | ${data.totalPassenger}"
        binding.toolbarChooseAirlines.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        data.airlinesFirst?.let { setDataFirstFlight(it) }
    }

    private fun setDataFirstFlight(dataAirlinesFirst:AirlinesFirst){
        val codeCityAndAirlineName = "${dataAirlinesFirst.cityCodeDeparture} - ${dataAirlinesFirst.cityCodeArrived} | ${dataAirlinesFirst.nameAirlines}"
        val dateAndTimeFlight = "${data.startDate} | ${dataAirlinesFirst.departureTime.substring(11, 19)} - ${dataAirlinesFirst.arrivedTime.substring(11, 19)}"
        val priceTicket = "${helper.convertToFormatMoneyIDRFilter(dataAirlinesFirst.priceTicket.toString())}/person"
        binding.includeFirstFlight.tvCodecityAndAirlineName.text = codeCityAndAirlineName
        binding.includeFirstFlight.tvDateFlightAndTime.text = dateAndTimeFlight
        binding.includeFirstFlight.tvPriceTicket.text = priceTicket
        
    }

    override fun onClick(view: View, data: DepartItem, timeFlight: List<TimeFlight?>?,  dataReturn: DepartItem) {
//        val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination, this.data.totalPassenger,
//            this.data.flightClass, this.data.airlinesFirst, airlinesSecond = AirlinesSecond(data.nameAirlines,
//                data.iconAirline, data.listFlightTime, data.flightTime, data.typeFlight,
//                data.cityCodeDeparture,data.cityCodeArrived, data.priceTicket, data.departureTime,
//                data.arrivedTime), null)
//        val bundle = Bundle()
//        bundle.putParcelable("data", dataFlight)
//        Navigation.findNavController(view).navigate(R.id.action_chooseAirlinesSecondFlightFragment_to_fillDataPassengerFragment, bundle)
    }

    override fun messageChooseAirline(msg: String) {
        Log.d("getAirlineSecond", msg)
    }

    override fun accessCode(accessCode: String?) {

    }

    override fun getDataAirline(data: DataAirline?) {
        TODO("Not yet implemented")
    }

}