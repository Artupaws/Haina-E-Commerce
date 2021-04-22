package haina.ecommerce.view.flight.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentBottomsheetFlightBinding


class BottomSheetFlightFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var _binding: FragmentBottomsheetFlightBinding
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
    ): View? {
        _binding = FragmentBottomsheetFlightBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarFlight.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarFlight.setNavigationOnClickListener { dismiss() }
        binding.toolbarFlight.title = "Select Passenger"
        binding.includeSelectPassenger.cvAddAdult.setOnClickListener(this)
        binding.includeSelectPassenger.cvAddKid.setOnClickListener(this)
        binding.includeSelectPassenger.cvAddBaby.setOnClickListener(this)
        binding.includeSelectPassenger.cvMinusAdult.setOnClickListener(this)
        binding.includeSelectPassenger.cvMinusKid.setOnClickListener(this)
        binding.includeSelectPassenger.cvMinusBaby.setOnClickListener(this)
        binding.includeSelectPassenger.btnSaveTotalPassenger.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetFlightFragment {
            val fragment = BottomSheetFlightFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun addAdults() {
        if (totalAdults < maxAdults) {
            totalAdults++
            binding.includeSelectPassenger.etTotalAdult.setText(totalAdults.toString())
        } else if (totalAdults == maxAdults) {
            totalAdults = maxAdults
            binding.includeSelectPassenger.etTotalAdult.setText(totalAdults.toString())
        }
    }

    private fun minusAdults() {
        if (totalAdults > 0) {
            totalAdults--
            binding.includeSelectPassenger.etTotalAdult.setText(totalAdults.toString())
        } else if (totalAdults == 0) {
            totalAdults = 0
            binding.includeSelectPassenger.etTotalAdult.setText(totalAdults.toString())
        }
    }

    private fun addKids() {
        if (totalKids < maxKids) {
            totalKids++
            binding.includeSelectPassenger.etTotalKid.setText(totalKids.toString())
        } else if (totalKids == maxKids) {
            totalKids = maxKids
            binding.includeSelectPassenger.etTotalKid.setText(totalKids.toString())
        }
    }

    private fun minusKids() {
        if (totalKids > 0) {
            totalKids--
            binding.includeSelectPassenger.etTotalKid.setText(totalKids.toString())
        } else if (totalKids == 0) {
            totalKids = 0
            binding.includeSelectPassenger.etTotalKid.setText(totalKids.toString())
        }
    }

    private fun addBaby() {
        if (totalBaby < maxBaby) {
            totalBaby++
            binding.includeSelectPassenger.etTotalBaby.setText(totalBaby.toString())
        } else if (totalBaby == maxBaby) {
            totalBaby = maxBaby
            binding.includeSelectPassenger.etTotalBaby.setText(totalBaby.toString())
        }
    }

    private fun minusBaby() {
        if (totalBaby > 0) {
            totalBaby--
            binding.includeSelectPassenger.etTotalBaby.setText(totalBaby.toString())
        } else if (totalBaby == 0) {
            totalBaby = 0
            binding.includeSelectPassenger.etTotalBaby.setText(totalBaby.toString())
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
                R.id.cv_add_baby -> {
                    addBaby()
                }
                R.id.cv_minus_baby -> {
                    minusBaby()
                }
                R.id.btn_save_total_passenger -> {
                    val intentDataPassenger =Intent("dataPassenger")
                        .putExtra("total", "$totalAdults Adult(s), $totalKids Kid(s), $totalBaby Baby(s)")
                    broadcaster?.sendBroadcast(intentDataPassenger)
                    dismiss()
                }
        }
    }


}