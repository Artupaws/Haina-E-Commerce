package haina.ecommerce.view.history.historytransaction.unfinish

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterTransactionUnfinish
import haina.ecommerce.databinding.FragmentTransactionUnfinishBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment
import haina.ecommerce.view.login.LoginActivity

class FragmentTransactionUnfinish : Fragment(), View.OnClickListener, BottomSheetHowToPayment.ItemClickListener,
    AdapterTransactionUnfinish.ItemAdapterCallback {

    private var _binding:FragmentTransactionUnfinishBinding? = null
    private val binding get()= _binding
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var sharedPref:SharedPreferenceHelper
    private var statusLogin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionUnfinishBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLogin = sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)
        showNotLogin(statusLogin)

        binding?.includeNotLogin?.btnLoginNotLogin?.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceive, IntentFilter("ListTransaction"))
    }

    private val mMessageReceive :BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "ListTransaction" -> {
//                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    val dataTransactionUnfinish = intent.getParcelableExtra<DataTransaction>("Transaction")
                    setupListUnfinishTransaction(dataTransactionUnfinish.pending)
                }
            }
        }
    }

    private fun setupListUnfinishTransaction(data:List<PendingItem?>?){
        showIsEmpty(data?.size)

        binding?.rvUnfinishTransaction?.apply {
            adapter = AdapterTransactionUnfinish(requireActivity(), data, this@FragmentTransactionUnfinish)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showNotLogin(statusLogin:Boolean){
        if (!statusLogin){
            binding?.includeNotLogin?.linearNotLogin?.visibility = View.VISIBLE
        } else {
            binding?.includeNotLogin?.linearNotLogin?.visibility = View.GONE
        }
    }

    private fun showIsEmpty(listItem:Int?){
        if (listItem == 0){
            binding?.rvUnfinishTransaction?.visibility = View.GONE
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceive)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onItemClick(item: String) {
        when(item){
            "" ->{
            }
        }
    }

    override fun onClick(view: View, data: PendingItem) {
        when(view.id){
            R.id.iv_option -> {
                val popup = PopupMenu(requireContext(), view)
                popup.inflate(R.menu.menu_cancel_transaction)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_cancel_transaction -> {
                            Toast.makeText(context, "${data.payment?.idPaymentMethod}", Toast.LENGTH_SHORT).show()
                            true
                        } else -> false
                    }
                }
                popup.show()
            }
            R.id.btn_how_pay -> {
                childFragmentManager.let {
                    BottomSheetHowToPayment.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
            }
        }
    }
}