package haina.ecommerce.view.hotels.listfacitieshotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterFacilitiesHotelMain
import haina.ecommerce.databinding.FragmentBottomSheetFacilitiesHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataRoom

class BottomSheetFacilitiesHotel : BottomSheetDialogFragment() {

    private lateinit var _binding : FragmentBottomSheetFacilitiesHotelBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetFacilitiesHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dataFacilities = arguments?.getParcelable<DataRoom>("data")
        binding.rvFacilities.apply {
            adapter = AdapterFacilitiesHotelMain(requireActivity(), dataFacilities?.hotelInfo?.facility)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        binding.toolbarFacilities.setNavigationOnClickListener {
            dismiss()
        }

    }

    companion object {
        @JvmStatic
        fun instanceBottomSheetFacilitiesHotel(bundle: Bundle): BottomSheetFacilitiesHotel {
            val fragment = BottomSheetFacilitiesHotel()
            fragment.arguments = bundle
            return fragment
        }
    }

}