package haina.ecommerce.view.hotels.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentBottomsheetFlightBinding
import haina.ecommerce.databinding.FragmentBottomsheetPaxHotelBinding
import haina.ecommerce.databinding.FragmentJobFilterBinding
import haina.ecommerce.model.hotels.HotelSearchItem
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment
import java.lang.RuntimeException

class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentJobFilterBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null

    private var minSalary:Int? = null
    private var idEdu:Int? = null
    private var idSpecialist:Int? = null
    private var idCity:Int? = null
    private var type:Int? = null
    private var level:Int? = null
    private var experience:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobFilterBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarHotelSelection.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotelSelection.setNavigationOnClickListener { dismiss() }
        binding.toolbarHotelSelection.title = "Select Passenger"
        binding.includePax.cvAddAdult.setOnClickListener(this)
        binding.includePax.cvAddKid.setOnClickListener(this)
        binding.includePax.cvMinusAdult.setOnClickListener(this)
        binding.includePax.cvMinusKid.setOnClickListener(this)
        binding.includePax.btnSaveTotalPassenger.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetHotelFragment {
            val fragment = BottomSheetHotelFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun passFilterData(){

        val intentDataFilter =Intent("jobFilter")
            .putExtra("minSalary", minSalary)
            .putExtra("idEdu",idEdu)
            .putExtra("idSpecialist",idSpecialist)
            .putExtra("idCity",idCity)
            .putExtra("type",type)
            .putExtra("level",level)
            .putExtra("experience",experience)
        broadcaster?.sendBroadcast(intentDataFilter)

    }
}