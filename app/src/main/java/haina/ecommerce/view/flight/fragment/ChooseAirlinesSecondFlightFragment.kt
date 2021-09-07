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
import haina.ecommerce.adapter.flight.AdapterAirlinesReturn
import haina.ecommerce.databinding.FragmentChooseAirlinesSecondFlightBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.flight.*

class ChooseAirlinesSecondFlightFragment : Fragment(), AdapterAirlinesReturn.ItemAdapterCallback {

    private lateinit var _binding: FragmentChooseAirlinesSecondFlightBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private val helper:Helper = Helper
    var airlineCode :String = ""
    var depart :DepartItem = DepartItem()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChooseAirlinesSecondFlightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable<Request>("data")!!
        airlineCode = arguments?.getString("airlineCode")!!
        depart = arguments?.getParcelable<DepartItem>("depart")!!
        showListAirlineReturn(data.airlineReturn?.filter { it?.airlineCode == airlineCode })
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

    override fun onClick(view: View, returnParams: DepartItem, timeFlight: List<TimeFlight?>?) {
        val dataFlight = Request(this.data.startDate, this.data.finishDate, this.data.fromDestination, this.data.toDestination, this.data.totalPassenger,
            this.data.totalAdult, this.data.totalChild, this.data.totalBaby,this.data.airlinesFirst, airlinesSecond = AirlinesSecond(returnParams.airlineCode!!,
                returnParams.airlineDetail?.image!!, null, returnParams.departTime!!, "",
                returnParams.origin!!,returnParams.destination!!, returnParams.price.toString(), returnParams.departTime.toString(),
                returnParams.arrivalTime!!),null, this.data.airlineDepart, this.data.airlineReturn)
        val bundle = Bundle()
        bundle.putParcelable("data", dataFlight)
        bundle.putParcelable("depart", depart)
        bundle.putParcelable("return", returnParams)
        bundle.putString("airlineCode", airlineCode)
        Navigation.findNavController(view).navigate(R.id.action_chooseAirlinesSecondFlightFragment_to_fillDataPassengerFragment, bundle)
    }

    private fun showListAirlineReturn(data: List<DepartItem?>?){
        binding.rvAirlines.apply {
            adapter = AdapterAirlinesReturn(requireActivity(), data!!, this@ChooseAirlinesSecondFlightFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }
}