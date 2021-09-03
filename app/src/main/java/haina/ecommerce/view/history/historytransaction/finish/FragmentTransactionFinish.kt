package haina.ecommerce.view.history.historytransaction.finish

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
import haina.ecommerce.adapter.AdapterTransactionPulsaFinish
import haina.ecommerce.databinding.FragmentTransactionFinishBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.model.transactionlist.ProcessItem
import haina.ecommerce.model.transactionlist.SuccessItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.detailtransaction.DetailTransactionActivity
import haina.ecommerce.view.login.LoginActivity
import timber.log.Timber

class FragmentTransactionFinish : Fragment(), View.OnClickListener, AdapterTransactionPulsaFinish.ItemAdapterCallback {

    private var _binding:FragmentTransactionFinishBinding? = null
    private val binding get() = _binding
    private var broadcaster : LocalBroadcastManager? = null
    private lateinit var sharedPref: SharedPreferenceHelper
    private var statusLogin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionFinishBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLogin = sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)
        showNotLogin (statusLogin)

        binding?.includeNotLogin?.btnLoginNotLogin?.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("ListTransaction"))
    }

    private val mMessageReceiver : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "ListTransaction" -> {
                    val listTransactionFinish = intent.getParcelableExtra<DataTransaction>("Transaction")
                    setupListTransactionFinish(listTransactionFinish)
                }
            }
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
            binding?.rvTransactionFinish?.visibility = View.GONE
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
    }

    private fun setupListTransactionFinish(data:DataTransaction?){
        Timber.d(data?.success?.size.toString())
        showIsEmpty(data?.success?.size)
        binding?.rvTransactionFinish?.apply {
            adapter = AdapterTransactionPulsaFinish(requireContext(), data?.success, this@FragmentTransactionFinish)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onClickAdapter(view: View, data: SuccessItem) {
        when(view.id){
            R.id.relative_click -> {
                val intent = Intent(context, DetailTransactionActivity::class.java)
                    .putExtra("transaction", "finish")
                    .putExtra("dataFinish", data)
                startActivity(intent)
            }
        }
    }
}