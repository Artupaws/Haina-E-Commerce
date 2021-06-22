package haina.ecommerce.view.hotels.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListHotelDarma
import haina.ecommerce.databinding.FragmentScheduleHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

class ScheduleHotelFragment : Fragment(), AdapterListHotelDarma.ItemAdapterCallBack {

    private lateinit var _binding:FragmentScheduleHotelBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScheduleHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dataHotelDarma = arguments?.getParcelable<DataHotelDarma>("dataHotel")

        val adapterHotel = AdapterListHotelDarma(requireActivity(), dataHotelDarma?.hotels, this)
        binding.rvHotels.apply {
            adapter = adapterHotel
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(view: View, idHotel: String) {
        when(view.id){
            R.id.cv_click -> {
                Toast.makeText(requireActivity(), idHotel, Toast.LENGTH_SHORT).show()
            }
        }
    }

}