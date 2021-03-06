package haina.ecommerce.view.history.historytransaction.cancel

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
import haina.ecommerce.adapter.AdapterTransactionPulsaCancel
import haina.ecommerce.databinding.FragmentTransactionCancelBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class FragmentTransactionCancel : Fragment(), View.OnClickListener {

    private var _binding:FragmentTransactionCancelBinding? = null
    private val binding get() = _binding
    private var broadcaster : LocalBroadcastManager? = null
    private lateinit var sharedPref: SharedPreferenceHelper
    private var statusLogin = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionCancelBinding.inflate(inflater, container, false)
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
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("ListTransaction"))
    }

    private val mMessageReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "ListTransaction" -> {
                    val listTransactionFinish = intent.getParcelableExtra<DataTransaction>("Transaction")
                    setupListTransactionCancel(listTransactionFinish)
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
            binding?.rvTransactionCancel?.visibility = View.GONE
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = "You haven't cancel any transaction"
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

    private fun setupListTransactionCancel(data:DataTransaction?){

        showIsEmpty(data?.canceled?.size)

        binding?.rvTransactionCancel?.apply {
            adapter = AdapterTransactionPulsaCancel(requireContext(), data?.canceled)
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

}