package haina.ecommerce.view.topup.pulsa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPulsa
import haina.ecommerce.databinding.FragmentPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.Pulsa
import haina.ecommerce.view.checkout.CheckoutActivity

class PulsaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPulsaBinding? = null
    private val binding get() = _binding
    private val helper:Helper = Helper()
    private val listPulsa = arrayListOf(Pulsa(nominal = "50.000", 50000),
    Pulsa(nominal = "25.000", 25000),
    Pulsa(nominal = "75.000", 75000),
    Pulsa(nominal = "100.000", 100000))
    private var broadcaster: LocalBroadcastManager? = null
    private var totalPrice:String? = null
    private var phoneNumber:String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPulsaBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterPulsa = AdapterPulsa(requireContext(), listPulsa)

        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        binding?.btnNext?.setOnClickListener(this)

        binding?.rvPulsa?.apply {
            adapter = adapterPulsa
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        adapterPulsa.onItemClick = { i: Int, s: String ->
            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
            binding?.tvPrice?.text = totalPrice
            if (i!=0){
                binding?.linearTotalPrice?.visibility = View.VISIBLE
            } else {
                binding?.linearTotalPrice?.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_next -> {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                        .putExtra("totalPrice", totalPrice)
                        .putExtra("phoneNumber", phoneNumber)
                        .putExtra("titleService", "Pulsa")
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("phoneNumber"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action){
                "phoneNumber" -> {
                    phoneNumber = intent.getStringExtra("number")
                    if (phoneNumber != ""){
                        binding?.tvNumberEmpty?.visibility = View.GONE
                        binding?.rvPulsa?.visibility = View.VISIBLE
                    }else{
                        binding?.tvNumberEmpty?.visibility = View.VISIBLE
                        binding?.rvPulsa?.visibility = View.GONE
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



}