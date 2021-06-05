package haina.ecommerce.view.hotels.transactionhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.TabAdapterHistoryHotelTransaction
import haina.ecommerce.databinding.ActivityHistoryTransactionBinding
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity

class HistoryTransactionHotelActivity : AppCompatActivity(), HistoryTransactionHotelContract, View.OnClickListener {

    private lateinit var binding:ActivityHistoryTransactionBinding
    private lateinit var presenter:HistoryTransactionHotelPresenter
    private var broadcaster:LocalBroadcastManager?=null
    private lateinit var sharedPref:SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HistoryTransactionHotelPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)
        sharedPref = SharedPreferenceHelper(this)
        startRequest(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN))

        binding.toolbarTransaction.title = getString(R.string.transaction_hotel)
        binding.toolbarTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTransaction.setNavigationOnClickListener { moveToDashboard() }
        binding.vpTransaction.adapter = TabAdapterHistoryHotelTransaction(supportFragmentManager, 0)
        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        startRequest(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN))
    }

    private fun startRequest(login:Boolean){
        when(login){
            true -> {
                binding.vpTransaction.visibility = View.VISIBLE
                binding.includeLogin.linearNotLogin.visibility = View.GONE
                presenter.getListTransactionHotel()
            } else -> {
            binding.vpTransaction.visibility = View.GONE
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        moveToDashboard()
    }

    private fun moveToDashboard(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun messageGetListTransactionHotel(msg: String) {
        Log.d("messageGetList", msg)
    }

    override fun getListTransactionHotel(dataHotel: DataTransactionHotel?) {
        val dataTransactionHotel = Intent("dataTransactionHotel")
            .putExtra("transactionHotel", dataHotel)
        broadcaster?.sendBroadcast(dataTransactionHotel)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

