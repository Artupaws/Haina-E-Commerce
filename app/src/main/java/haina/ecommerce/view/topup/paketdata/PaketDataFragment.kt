package haina.ecommerce.view.topup.paketdata

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
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPaketData
import haina.ecommerce.databinding.FragmentPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.PaketData
import haina.ecommerce.view.checkout.CheckoutActivity

class PaketDataFragment : Fragment(), View.OnClickListener {

    private var _binding:FragmentPaketDataBinding? = null
    private val binding get() = _binding
    private val listPaketData = arrayListOf(PaketData(title = "bronet 4g owsem 1gb", 50000, description = "1gb kuota utama"),
            PaketData(title = "bronet 4g owsem 2gb", 25000, "2gb kuota utama"),
            PaketData(title = "bronet 4g owsem 3gb", 75000, "3gb kuota utama"),
            PaketData(title = "bronet 4g owsem 4gb", 100000, "4gb kuota utama"))
    private var totalPrice:String? = null
    private var phoneNumber:String? = null
    private val helper:Helper = Helper()
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPaketDataBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        val adapterPaketData = AdapterPaketData(requireContext(), listPaketData)

        binding?.btnNext?.setOnClickListener(this)

        binding?.rvPaketData?.apply {
            adapter = adapterPaketData
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        adapterPaketData.onItemClick = { i: Int, s: String ->
            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
            binding?.tvPrice?.text = totalPrice
            if (i!=0){
                binding?.linearTotalPrice?.visibility = View.VISIBLE
            } else {
                binding?.linearTotalPrice?.visibility = View.GONE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_next -> {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                        .putExtra("totalPrice", totalPrice)
                        .putExtra("phoneNumber", phoneNumber)
                        .putExtra("titleService", "Paket Data")
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
                        binding?.rvPaketData?.visibility = View.VISIBLE
                    }else{
                        binding?.tvNumberEmpty?.visibility = View.VISIBLE
                        binding?.rvPaketData?.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
    }


}