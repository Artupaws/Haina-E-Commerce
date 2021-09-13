package haina.ecommerce.view.topup.pulsa

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPulsa
import haina.ecommerce.databinding.FragmentPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.PulsaItem
import haina.ecommerce.model.pulsaanddata.RequestPulsa
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.view.checkout.CheckoutActivity
import haina.ecommerce.view.topup.TopupActivity

class PulsaFragment : Fragment(), View.OnClickListener, PulsaContract.View, AdapterPulsa.ItemAdapterCallback {

    private var _binding: FragmentPulsaBinding? = null
    private val binding get() = _binding
    private val helper: Helper = Helper
    private lateinit var presenter: PulsaPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var totalPrice: String? = null
    private var phoneNumber: String? = null
    private var serviceType: String? = null
    private var productCode: String? = null
    private lateinit var sharedPref: SharedPreferenceHelper
    private var typeTransaction:Int = 1
    private var progressDialog: Dialog? = null

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
        dialogLoading()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                checkDataPulsa()
            }
        }
    }

    private fun checkDataPulsa(){
        val phoneNumber = (activity as TopupActivity).getNumber()
        val idProductParams = productCode
        val totalPriceParams = totalPrice
        val typeService = serviceType
        when {
            phoneNumber.isNullOrEmpty()->{
                Toast.makeText(requireActivity(), "Phone number empty", Toast.LENGTH_SHORT).show()
            }
            idProductParams == null ->{
                Toast.makeText(requireActivity(), "Please choose product", Toast.LENGTH_SHORT).show()
            } else -> {
            val dataPulsa = RequestPulsa(phoneNumber, idProductParams, null, totalPriceParams!!, typeService!!, 0)
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
//        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("resetPrice"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "productPhone" -> {
                    val dataProductPhone = intent.getParcelableExtra<ProductPhone>("pulsa")
                    if (dataProductPhone?.provider?.name.isNullOrEmpty()){
                        binding?.tvNumberEmpty?.visibility = View.VISIBLE
                    } else {
                        getListPulsa(dataProductPhone)
                    }
                }
//                "resetPrice" -> {
//                    val statusResetPrice = intent.getStringExtra("reset")
//                    Toast.makeText(activity, statusResetPrice, Toast.LENGTH_SHORT).show()
//                    if (statusResetPrice == "true") {
//                        binding?.linearTotalPrice?.visibility = View.GONE
//                        binding?.tvPrice?.text = ""
//                    }
//                }
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
    
    fun getListPulsa(data: ProductPhone?) {
        Log.d("itemPulsa", data?.group?.pulsa?.size.toString())
        val adapterPulsa = AdapterPulsa(requireContext(), data?.group?.pulsa, this@PulsaFragment)
//        if (data?.group?.pulsa?.isEmpty() == true) {
//            binding?.tvNumberEmpty?.visibility = View.VISIBLE
//            binding?.rvPulsa?.visibility = View.GONE
//        } else {
            binding?.tvNumberEmpty?.visibility = View.GONE
            binding?.rvPulsa?.visibility = View.VISIBLE
            binding?.rvPulsa?.apply {
                adapter = adapterPulsa
                layoutManager = GridLayoutManager(requireContext(), 2)
//            }
        }

//        adapterPulsa.indexChoose = {i:Int->
//            val index:Int = i
//            if (index == -1){
//                binding?.linearTotalPrice?.visibility = View.GONE
//                binding?.tvPrice?.text = ""
//            }
//        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),R.color.white))
        val window : Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun getProductPhone(data: ProductPhone?) {
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClickAdapter(view: View, data: PulsaItem) {
        totalPrice = helper.convertToFormatMoneyIDRFilter(data.sellPrice.toString())
        serviceType = data.description
        productCode = data.productCode
        binding?.tvPrice?.text = totalPrice
        if (data.sellPrice!= 0) {
            binding?.linearTotalPrice?.visibility = View.VISIBLE
        } else {
            binding?.linearTotalPrice?.visibility = View.GONE
        }
    }


}