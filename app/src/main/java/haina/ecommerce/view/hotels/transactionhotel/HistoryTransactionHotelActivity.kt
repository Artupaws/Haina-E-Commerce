package haina.ecommerce.view.hotels.transactionhotel

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.TabAdapterHistoryHotelTransaction
import haina.ecommerce.countdowntimer.SimpleCountDownTimerKotlin
import haina.ecommerce.databinding.ActivityHistoryTransactionBinding
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity

class HistoryTransactionHotelActivity : AppCompatActivity(), HistoryTransactionHotelContract.View, View.OnClickListener {

    private lateinit var binding:ActivityHistoryTransactionBinding
    private lateinit var presenter:HistoryTransactionHotelPresenter
    private var broadcaster:LocalBroadcastManager?=null
    private lateinit var sharedPref:SharedPreferenceHelper
    private var progressDialog:Dialog? = null
    private var countDown: TextView? = null

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
        when(intent.getStringExtra("tabs")){
            "unfinish" -> {
                binding.vpTransaction.currentItem = 0
            }
            "finish" -> {
                binding.vpTransaction.currentItem = 1
            }
        }
        refresh()
        dialogLoading()
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
                presenter.getListTransactionHotelDarma()
            } else -> {
            binding.vpTransaction.visibility = View.GONE
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun onBackPressed() {
        moveToDashboard()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListTransactionHotel()
        }
    }

    private fun moveToDashboard(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun cancelBookingHotel(bookingId:Int){
        presenter.cancelBookingHotel(bookingId)
    }

    override fun messageGetListTransactionHotel(msg: String) {
        Log.d("messageGetList", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getListTransactionHotel(dataHotel: DataTransactionHotel?) {
//        val dataTransactionHotel = Intent("dataTransactionHotel")
//            .putExtra("transactionHotel", dataHotel)
//        broadcaster?.sendBroadcast(dataTransactionHotel)
    }

    override fun getListBookingHotelDarma(dataHotel: DataBooking?) {
        val dataTransactionHotel = Intent("dataBooking")
            .putExtra("bookingHotel", dataHotel)
        broadcaster?.sendBroadcast(dataTransactionHotel)
    }

    override fun messageCancelBookingHotel(msg: String) {
        if(msg.contains("Booking cancelled!")){
            presenter.getListTransactionHotelDarma()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
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

