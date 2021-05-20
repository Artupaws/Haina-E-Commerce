package haina.ecommerce.view.hotels.transactionhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.TabAdapterHistoryHotelTransaction
import haina.ecommerce.databinding.ActivityHistoryTransactionBinding
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.view.MainActivity

class HistoryTransactionHotelActivity : AppCompatActivity(), HistoryTransactionHotelContract {

    private lateinit var binding:ActivityHistoryTransactionBinding
    private lateinit var presenter:HistoryTransactionHotelPresenter
    private var broadcaster:LocalBroadcastManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HistoryTransactionHotelPresenter(this, this)
        presenter.getListTransactionHotel()
        broadcaster = LocalBroadcastManager.getInstance(this)

        binding.toolbarTransaction.title = "Transaction Hotel"
        binding.toolbarTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTransaction.setNavigationOnClickListener { moveToDashboard() }
        binding.vpTransaction.adapter = TabAdapterHistoryHotelTransaction(supportFragmentManager, 0)
        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)

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
}

