package haina.ecommerce.view.history.historysubmitapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterHistorySubmitJob
import haina.ecommerce.adapter.property.TabAdapterMyProperty
import haina.ecommerce.adapter.vacancy.AdapterListMyApplication
import haina.ecommerce.databinding.ActivityMyApplicationBinding
import haina.ecommerce.databinding.ActivityMyPropertyBinding
import haina.ecommerce.databinding.FragmentHistorySubmitApplicationBinding
import haina.ecommerce.model.DataJobApplication
import haina.ecommerce.model.vacancy.MyApplication
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.history.historymyproperty.MyPropertyPresenter
import haina.ecommerce.view.login.LoginActivity
import java.util.ArrayList

class HistorySubmitJobActivity : AppCompatActivity(), HistorySubmitJobContract, View.OnClickListener, AdapterListMyApplication.AdapterCallbackApplication {

    private var binding: ActivityMyApplicationBinding? = null
    private lateinit var presenter: HistorySubmitJobPresenter
    private var broadcaster: LocalBroadcastManager? = null

    private var totalSubmit:String = ""
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyApplicationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        presenter = HistorySubmitJobPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)
        binding!!.toolbarHistory!!.setNavigationOnClickListener { onBackPressed() }
        sharedPreferenceHelper = SharedPreferenceHelper(this)
        refresh()
        presenter.getJobSubmit()
        binding?.includeLogin?.btnLoginNotLogin?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getJobSubmit()
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding?.includeLogin?.linearNotLogin?.visibility = View.INVISIBLE
            binding?.rvHistoryJobSubmit?.visibility = View.VISIBLE
        } else {
            binding?.includeLogin?.linearNotLogin?.visibility = View.VISIBLE
            binding?.rvHistoryJobSubmit?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getJobSubmit()
    }


    override fun getListSubmitJob(item: List<MyApplication?>?) {


        val adapterListMyApplication =  AdapterListMyApplication(this, item as ArrayList<MyApplication?>,this)
        binding?.rvHistoryJobSubmit?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterListMyApplication
            adapterListMyApplication?.notifyDataSetChanged()
        }
        totalSubmit = "Total submit : ${item?.size.toString()} Application"
        binding?.tvTotalSubmit?.text = totalSubmit
    }

    override fun messageGetSubmitJobSuccess(msg: String) {
        Log.d("historyJobSubmitSuccess", msg)
        if (msg.contains("Success")){
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvHistoryJobSubmit?.visibility = View.VISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.rvHistoryJobSubmit?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun messageGetSubmitJobError(msg: String) {
        Log.d("historyJobSubmitError", msg)
        if (msg == "null" && sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = "You haven't applied"
            binding?.rvHistoryJobSubmit?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvHistoryJobSubmit?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun listApplicationClick(view: View, dataApplication: MyApplication) {

    }


}