package haina.ecommerce.view.topup.paketdata

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
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPaketData
import haina.ecommerce.adapter.AdapterPaketDataName
import haina.ecommerce.databinding.FragmentPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.DataItem
import haina.ecommerce.model.pulsaanddata.PaketDataItem
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.view.checkout.CheckoutActivity

class PaketDataFragment : Fragment(), View.OnClickListener, PaketDataContract {

    private var _binding:FragmentPaketDataBinding? = null
    private val binding get() = _binding
    private var totalPrice:String? = null
    private var phoneNumber:String? = null
    private val helper:Helper = Helper()
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var presenter: PaketDataPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPaketDataBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        presenter = PaketDataPresenter(this, requireContext())

//        val adapterPaketData = AdapterPaketData(requireContext(), listPaketData)

        binding?.btnNext?.setOnClickListener(this)

//        adapterPaketData.onItemClick = { i: Int, s: String ->
//            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
//            binding?.tvPrice?.text = totalPrice
//            if (i!=0){
//                binding?.linearTotalPrice?.visibility = View.VISIBLE
//            } else {
//                binding?.linearTotalPrice?.visibility = View.GONE
//            }
//        }

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
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("productPhone"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action){

                "productPhone" -> {
                    val dataProductPhone = intent.getParcelableExtra<ProductPhone>("pulsa")
                    getListPulsa(dataProductPhone)
                }
            }
        }
    }

    private fun getListPulsa(data: ProductPhone?) {
        val adapterPaketDataName = AdapterPaketDataName(requireContext(), data?.group?.data)
        binding?.tvNumberEmpty?.visibility = View.GONE
        binding?.rvPaketData?.visibility = View.VISIBLE
        binding?.rvPaketData?.apply {
            adapter = adapterPaketDataName
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        adapterPaketDataName.onItemClick={i:Int, s:String ->
            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
            binding?.tvPrice?.text = totalPrice
            if (i!=0){
                binding?.linearTotalPrice?.visibility = View.VISIBLE
            } else {
                binding?.linearTotalPrice?.visibility = View.GONE
            }
        }

    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
    }

    override fun messageCheckProviderAndProduct(msg: String) {
        Log.d("paketData", msg)
    }

    override fun getProductPhone(data: ProductPhone?) {
        Log.d("paketDataItem", data?.group?.data?.size.toString())
        binding?.rvPaketData?.apply {
            adapter = AdapterPaketDataName(requireContext(), data?.group?.data)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }


}