package haina.ecommerce.view.topup.pulsa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPulsa
import haina.ecommerce.databinding.ActivityTopupBinding
import haina.ecommerce.databinding.FragmentPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.checkout.CheckoutActivity
import haina.ecommerce.view.topup.TopupActivity

class PulsaFragment : Fragment(), View.OnClickListener, PulsaContract {

    private var _binding: FragmentPulsaBinding? = null
    private val binding get() = _binding
    private val helper: Helper = Helper()
    private lateinit var presenter: PulsaPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var totalPrice: String? = null
    private var phoneNumber: String? = null
    private var serviceType: String? = null
    private lateinit var sharedPref: SharedPreferenceHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPulsaBinding.inflate(inflater, container, false)
        presenter = PulsaPresenter(this, requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        binding?.btnNext?.setOnClickListener(this)
//        resetPrice()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                        .putExtra("totalPrice", totalPrice)
                        .putExtra("titleService", "Pulsa")
                        .putExtra("serviceType", serviceType)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("productPhone"))
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("resetPrice"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "productPhone" -> {
                    val dataProductPhone = intent.getParcelableExtra<ProductPhone>("pulsa")
                    getListPulsa(dataProductPhone)
                }
                "resetPrice" -> {
                    val statusResetPrice = intent.getStringExtra("reset")
                    Toast.makeText(activity, statusResetPrice, Toast.LENGTH_SHORT).show()
                    if (statusResetPrice == "true") {
                        binding?.linearTotalPrice?.visibility = View.GONE
                        binding?.tvPrice?.text = ""
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun messageCheckProviderAndProduct(msg: String) {
        Log.d("getPulsa", msg)
    }

    fun resetPrice() {
        binding?.linearTotalPrice?.visibility = View.GONE
        binding?.tvPrice?.text = ""
    }

    fun getListPulsa(data: ProductPhone?) {
        Log.d("itemPulsa", data?.group?.pulsa?.size.toString())
        val adapterPulsa = AdapterPulsa(requireContext(), data?.group?.pulsa)
        if (data?.group?.pulsa?.isEmpty() == true) {
            binding?.tvNumberEmpty?.visibility = View.VISIBLE
            binding?.rvPulsa?.visibility = View.GONE
        } else {
            binding?.tvNumberEmpty?.visibility = View.GONE
            binding?.rvPulsa?.visibility = View.VISIBLE
            binding?.rvPulsa?.apply {
                adapter = adapterPulsa
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }

        adapterPulsa.onItemClick = { i: Int, s: String ->
            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
            serviceType = s
            binding?.tvPrice?.text = totalPrice
            if (i!= 0) {
                binding?.linearTotalPrice?.visibility = View.VISIBLE
            } else {
                binding?.linearTotalPrice?.visibility = View.GONE
            }
        }

        adapterPulsa.indexChoose = {i:Int->
            val index:Int = i
            if (index == -1){
                binding?.linearTotalPrice?.visibility = View.GONE
                binding?.tvPrice?.text = ""
            }
        }
    }

    override fun getProductPhone(data: ProductPhone?) {
    }


}