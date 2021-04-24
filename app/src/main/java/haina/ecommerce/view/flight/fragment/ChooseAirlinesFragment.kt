package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.flight.AdapterAirlines
import haina.ecommerce.databinding.FragmentChooseAirlinesBinding
import haina.ecommerce.model.flight.Airlines
import haina.ecommerce.model.flight.Request
import haina.ecommerce.model.flight.TimeFlight

class ChooseAirlinesFragment : Fragment(), AdapterAirlines.ItemAdapterCallback {

    private lateinit var _binding: FragmentChooseAirlinesBinding
    private val binding get() = _binding
    private lateinit var data: Request
    private val listTimeFlight = listOf<TimeFlight>(
        TimeFlight("05:55", "06:55")
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseAirlinesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        data = arguments?.getParcelable<Request>("data")!!
        binding.toolbarChooseAirlines.title = "${data.fromDestination} - ${data.toDestination}"
        binding.toolbarChooseAirlines.subtitle = "${data.startDate} | ${data.totalPassenger}"
        binding.toolbarChooseAirlines.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvAirlines.apply {
            adapter = AdapterAirlines(requireActivity(), listAirlines, this@ChooseAirlinesFragment)
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(view:View, dataParams: Airlines) {
        val dataFlight = Request(data.startDate, data.finishDate, data.fromDestination, data.toDestination, data.totalPassenger,
            data.flightClass, dataParams, null, null)
        val bundle = Bundle()
        bundle.putParcelable("data", dataFlight)
        if (data.finishDate?.contains("select date")!!){
            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_fillDataPassengerFragment, bundle)
        } else if (!data.finishDate?.contains("select date")!!){
            Navigation.findNavController(view).navigate(haina.ecommerce.R.id.action_chooseAirlinesFragment_to_chooseAirlinesSecondFlightFragment, bundle)
        }
    }

}