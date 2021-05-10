package haina.ecommerce.view.hotels.transactionhotel.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentUnfinishBinding

class FinishHotelFragment : Fragment() {

    private lateinit var _binding:FragmentUnfinishBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUnfinishBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}