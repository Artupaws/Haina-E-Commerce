package haina.ecommerce.view.history.historytransaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.TabAdapterHistoryTransaction
import haina.ecommerce.databinding.ActivityHistoryTransactionBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.view.MainActivity

class HistoryTransactionActivity : AppCompatActivity(), HistoryTransactionContract {

    private lateinit var binding:ActivityHistoryTransactionBinding
    private lateinit var presenter: HistoryTransactionPresenter
    private var broadcaster:LocalBroadcastManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HistoryTransactionPresenter(this, this)
        presenter.getListTransaction()
        broadcaster = LocalBroadcastManager.getInstance(this)
        binding.toolbarTransaction.title = "History Transaction"
        binding.vpTransaction.adapter = TabAdapterHistoryTransaction(supportFragmentManager, 0)
        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)
        binding.toolbarTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTransaction.setNavigationOnClickListener { onBackPressed() }
        when(intent.getStringExtra("tabs")){
            "unfinish" -> {
                binding.vpTransaction.currentItem = 0
            }
            "finish" -> {
                binding.vpTransaction.currentItem = 1
            }
            "cancel" -> {
                binding.vpTransaction.currentItem = 2
            }
        }
        refresh()
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListTransaction()
        }
    }

    override fun messageGetListTransaction(msg: String) {
        Log.d("getListTransaction", msg)
        binding.swipeRefresh.isRefreshing = false
        if (!msg.contains("Success")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getListTransaction(data: DataTransaction?) {
        val intentTransactionUnfinish = Intent("ListTransaction")
                .putExtra("Transaction", data)
        broadcaster?.sendBroadcast(intentTransactionUnfinish)
    }
}