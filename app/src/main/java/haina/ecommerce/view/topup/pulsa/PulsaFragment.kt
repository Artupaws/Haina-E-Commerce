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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterPulsa
import haina.ecommerce.databinding.FragmentPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.PulsaItem
import haina.ecommerce.view.checkout.CheckoutActivity

class PulsaFragment : Fragment(), View.OnClickListener, PulsaContract {

    private var _binding: FragmentPulsaBinding? = null
    private val binding get() = _binding
    private val helper:Helper = Helper()
    private lateinit var presenter:PulsaPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var totalPrice:String? = null
    private var phoneNumber:String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPulsaBinding.inflate(inflater, container, false)
        presenter = PulsaPresenter(this, requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        binding?.btnNext?.setOnClickListener(this)
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
                    presenter.checkProvider(phoneNumber!!)
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

    override fun messageCheckProviderAndProduct(msg: String) {
        Log.d("getPulsa", msg)
    }

    override fun getProductPhone(data: ProductPhone?) {
        Log.d("item", data.toString())
        binding?.tvNumberEmpty?.visibility = View.GONE
        binding?.rvPulsa?.visibility = View.VISIBLE
        binding?.rvPulsa?.apply {
            adapter = AdapterPulsa(requireContext(), data?.group?.pulsa)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

//        adapterPulsa.onItemClick = { i: Int, s: String ->
//            totalPrice = helper.convertToFormatMoneyIDRFilter(i.toString())
//            binding?.tvPrice?.text = totalPrice
//            if (i!=0){
//                binding?.linearTotalPrice?.visibility = View.VISIBLE
//            } else {
//                binding?.linearTotalPrice?.visibility = View.GONE
//            }
//        }
    }


}