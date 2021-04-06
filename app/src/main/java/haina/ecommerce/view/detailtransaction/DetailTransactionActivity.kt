package haina.ecommerce.view.detailtransaction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailTransactionBinding
import haina.ecommerce.model.transactionlist.CancelItem
import haina.ecommerce.model.transactionlist.ProcessItem

class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailTransactionBinding
    private var dataFinish : ProcessItem? = null
    private var dataCancel : CancelItem? = null
    private var openDetailTransaction : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataCancel = intent.getParcelableExtra("dataCancel")
        dataFinish = intent.getParcelableExtra("dataFinish")
        openDetailTransaction = intent.getStringExtra("transaction")

        if (openDetailTransaction == "finish"){
            setupDetailTransactionFinish(dataFinish)
        } else {
            setupDetailTransactionCancel(dataCancel)
        }

        binding.toolbarDetailTransaction.title = "Detail Transaction"
        binding.toolbarDetailTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailTransaction.setNavigationIcon(R.drawable.ic_back_black)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataCancel = null
        dataFinish = null
        openDetailTransaction = null
    }

    private fun setupDetailTransactionFinish(data:ProcessItem?){
        binding.tvStatusTransaction.text = data?.status
        binding.tvTransactionDate.text = data?.transactionTime
        binding.tvTotalPaymentBottom.text = data?.totalPayment.toString()
        if (data?.payment?.idPaymentMethod == 1){
            binding.tvPaymentMethod.text = getString(R.string.virtual_account)
        } else {
            binding.tvPaymentMethod.text = getString(R.string.bank_transfer)
        }
    }

    private fun setupDetailTransactionCancel(data:CancelItem?){
        binding.tvStatusTransaction.text = data?.status
        binding.tvTransactionDate.text = data?.transactionTime
        binding.tvTotalPaymentBottom.text = data?.totalPayment.toString()
        if (data?.payment?.idPaymentMethod == 1){
            binding.tvPaymentMethod.text = getString(R.string.virtual_account)
        } else {
            binding.tvPaymentMethod.text = getString(R.string.bank_transfer)
        }
    }

}