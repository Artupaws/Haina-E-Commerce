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
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment
import java.lang.RuntimeException

class BottomSheetHotelFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var _binding: FragmentBottomsheetPaxHotelBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null
    private var totalAdults: Int = 0
    private var maxAdults: Int = 4
    private var maxKids: Int = 4
    private var maxBaby: Int = 2
    private var totalKids: Int = 0
    private var totalBaby: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomsheetPaxHotelBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarHotelSelection.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotelSelection.setNavigationOnClickListener { dismiss() }
        binding.toolbarHotelSelection.title = getString(R.string.select_passenger)
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

    private fun addAdults() {
        if (totalAdults < maxAdults) {
            totalAdults++
            binding.includePax.etTotalAdult.setText(totalAdults.toString())
        } else if (totalAdults == maxAdults) {
            totalAdults = maxAdults
            binding.includePax.etTotalAdult.setText(totalAdults.toString())
        }
    }

    private fun minusAdults() {
        if (totalAdults > 0) {
            totalAdults--
            binding.includePax.etTotalAdult.setText(totalAdults.toString())
        } else if (totalAdults == 0) {
            totalAdults = 0
            binding.includePax.etTotalAdult.setText(totalAdults.toString())
        }
    }

    private fun addKids() {
        if (totalKids < maxKids) {
            totalKids++
            binding.includePax.etTotalKid.setText(totalKids.toString())
        } else if (totalKids == maxKids) {
            totalKids = maxKids
            binding.includePax.etTotalKid.setText(totalKids.toString())
        }
    }

    private fun minusKids() {
        if (totalKids > 0) {
            totalKids--
            binding.includePax.etTotalKid.setText(totalKids.toString())
        } else if (totalKids == 0) {
            totalKids = 0
            binding.includePax.etTotalKid.setText(totalKids.toString())
        }
    }


    override fun onClick(v: View?) {
            when (v?.id) {
                R.id.cv_add_adult -> {
                    addAdults()
                }
                R.id.cv_minus_adult -> {
                    minusAdults()
                }
                R.id.cv_add_kid -> {
                    addKids()
                }
                R.id.cv_minus_kid -> {
                    minusKids()
                }
                R.id.btn_save_total_passenger -> {
                    checkTotalPassenger()
                    dismiss()
                }
        }
    }

    private fun checkTotalPassenger(){
        var totalAdult = binding.includePax.etTotalAdult.text.toString()
        val totalChild = binding.includePax.etTotalKid.text.toString()

        if (totalAdult == "0"){
            binding.includePax.etTotalAdult.error = getString(R.string.input_adult)
        } else {
            totalAdult = binding.includePax.etTotalAdult.text.toString()
            val intentDataPassenger =Intent("dataPassenger")
                .putExtra("totalAdult", totalAdult)
                .putExtra("totalChild", totalChild)
                .putExtra("total", "${(totalAdult.toInt()+totalChild.toInt()+totalBaby.toInt())}")
            broadcaster?.sendBroadcast(intentDataPassenger)
        }
    }
}