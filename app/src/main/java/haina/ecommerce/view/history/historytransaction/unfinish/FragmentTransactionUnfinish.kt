package haina.ecommerce.view.history.historytransaction.unfinish

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.AdapterTransactionUnfinish
import haina.ecommerce.databinding.FragmentTransactionUnfinishBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.model.transactionlist.PendingItem

class FragmentTransactionUnfinish : Fragment(), View.OnClickListener {

    private var _binding:FragmentTransactionUnfinishBinding? = null
    private val binding get()= _binding
    private var titleService:String = ""
    private var totalPayment:String = ""
    private var paymentMethod:String = ""
    private var broadcaster:LocalBroadcastManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionUnfinishBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceive, IntentFilter("ListTransaction"))
    }

    private val mMessageReceive :BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "ListTransaction" -> {
                    val dataTransactionUnfinish = intent.getParcelableExtra<DataTransaction>("Transaction")
                    setupListUnfinishTransaction(dataTransactionUnfinish.pending)
                }
            }
        }
    }

    private fun setupListUnfinishTransaction(data:List<PendingItem?>?){
        binding?.rvUnfinishTransaction?.apply {
            adapter = AdapterTransactionUnfinish(requireContext(), data)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceive)
    }

    override fun onClick(v: View?) {

    }
}