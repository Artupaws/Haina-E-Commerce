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
import haina.ecommerce.adapter.AdapterPaketDataName
import haina.ecommerce.databinding.FragmentPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.RequestPulsa
import haina.ecommerce.view.checkout.CheckoutActivity
import haina.ecommerce.view.topup.TopupActivity

class PaketDataFragment : Fragment(), View.OnClickListener, PaketDataContract {

    private var _binding:FragmentPaketDataBinding? = null
    private val binding get() = _binding
    private var totalPrice:String? = null
    private var phoneNumber:String? = null
    private val helper:Helper = Helper
    private var broadcaster: LocalBroadcastManager? = null
    private var serviceType: String? = null
    private var statusResetPrice:String? = null
    private var productCode:String? = null
    private var typeTransaction:Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPaketDataBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        binding?.btnNext?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_next -> {
                checkDataPulsa()
            }
        }
    }

    private fun checkDataPulsa(){
        val phoneNumber = (activity as TopupActivity).getNumber()
        val productCodeParams = productCode
        val totalPriceParams = helper.convertToFormatMoneyIDRFilter(totalPrice)
        val typeService = serviceType
        when {
            phoneNumber.isNullOrEmpty()->{
                Toast.makeText(requireActivity(), "Phone number empty", Toast.LENGTH_SHORT).show()
            }
            productCodeParams == null ->{
                Toast.makeText(requireActivity(), "Please choose product", Toast.LENGTH_SHORT).show()
            } else -> {
            val dataPulsa = RequestPulsa(phoneNumber, productCodeParams, null, totalPriceParams!!, typeService!!, 0)
            val intentToCheckOut = Intent(requireActivity(), CheckoutActivity::class.java)
                .putExtra("dataPulsa", dataPulsa)
                .putExtra("typeTransaction", typeTransaction)
            startActivity(intentToCheckOut)
        }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("productPhone"))
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("paketData"))
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("resetPrice"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action){
                "productPhone" -> {
                    val dataProductPhone = intent.getParcelableExtra<ProductPhone>("pulsa")
                    getListPulsa(dataProductPhone)
                }
                "paketData" -> {
                    val nameService = intent.getStringExtra("serviceType")
                    val price = intent.getStringExtra("sellPrice")
                    val productCodeParams = intent.getStringExtra("productCode")
                    productCode = productCodeParams
                    serviceType = nameService
                    totalPrice = price
                    binding?.tvPrice?.text = helper.convertToFormatMoneyIDRFilter(totalPrice!!)
                    showTotalPrice(totalPrice)
                }
                "resetPrice" -> {
                    val statusReset = intent.getStringExtra("reset")
                    resetPrice(statusReset)
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
    }

    private fun showTotalPrice(totalPrice:String?){
        if (totalPrice!="0"){
            binding?.linearTotalPrice?.visibility = View.VISIBLE
        }
    }

    private fun resetPrice(statusReset:String?){
        if (statusReset == "true"){
            binding?.linearTotalPrice?.visibility = View.GONE
            binding?.tvPrice?.text = ""
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