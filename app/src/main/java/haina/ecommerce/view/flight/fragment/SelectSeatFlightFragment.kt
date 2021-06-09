package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import haina.ecommerce.databinding.FragmentSelectSeatFlightBinding

class SelectSeatFlightFragment : Fragment() {

    private lateinit var _binding : FragmentSelectSeatFlightBinding
    private val binding get() = _binding

    var seatFlight = ("AAAAAAAAAA_A_AAAAAAAAAAAAAAAAAA/"
            +"_____________________________/"
            +"AAAAAAAAAA_A_AAAAAAAAAAAAAAAAAA/"
            +"_____________________________/"
            +"AAAAAAAAAA_A_AAAAAAAAAAAAAAAAAAAA/"
            +"_____________________________A/"
            +"AAAAAAAAAA_A_AAAAAAAAAAAAAAAAAAAA/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectSeatFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}