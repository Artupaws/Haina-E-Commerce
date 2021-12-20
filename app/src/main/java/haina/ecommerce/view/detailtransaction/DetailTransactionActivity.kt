package haina.ecommerce.view.detailtransaction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityDetailTransactionBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.transactionlist.CancelItem
import haina.ecommerce.model.transactionlist.ProcessItem
import haina.ecommerce.model.transactionlist.SuccessItem

class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailTransactionBinding
    private var dataFinish : SuccessItem? = null
    private var dataCancel : CancelItem? = null
    private var openDetailTransaction : String? = null
    private val helper:Helper = Helper

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

        binding.toolbarDetailTransaction.title = getString(R.string.detail_transaction)
        binding.toolbarDetailTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarDetailTransaction.setNavigationIcon(R.drawable.ic_back_black)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataCancel = null
        dataFinish = null
        openDetailTransaction = null
    }

    private fun setupDetailTransactionFinish(data:SuccessItem?){
        binding.includeDetailBill.linearCustomerName.visibility = View.GONE
        binding.tvStatusTransaction.text = data?.status
        binding.tvTransactionDate.text = data?.transactionTime
        binding.tvCategoryProduct.text= data?.product?.description
        if (data?.payment?.idPaymentMethod == 1){
            binding.tvPaymentMethod.text = getString(R.string.virtual_account)
        } else {
            binding.tvPaymentMethod.text = getString(R.string.bank_transfer)
        }
        binding.includeDetailBill.tvServiceType.text = data?.product?.description
        binding.includeDetailBill.tvCustomerNumber.text = data?.customerNumber
        binding.includeDetailBill.tvTransactionNumber.text = data?.orderId
        val bill = data?.totalPayment?.minus(data.profit!!)
        binding.includeDetailBill.tvTotalBill.text = helper.convertToFormatMoneyIDRFilter(bill.toString())
        binding.includeDetailBill.tvAdminFee.text = helper.convertToFormatMoneyIDRFilter(data?.profit.toString())
        binding.includeDetailBill.tvTotalPayment.text = helper.convertToFormatMoneyIDRFilter(data?.totalPayment.toString())
        binding.tvTotalPaymentBottom.text = helper.convertToFormatMoneyIDRFilter(data?.totalPayment.toString())
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
        binding.includeDetailBill.tvServiceType.text = data?.product?.description
        binding.includeDetailBill.tvCustomerNumber.text = data?.customerNumber
        val bill = data?.totalPayment?.minus(data.profit!!)
        binding.includeDetailBill.tvTotalBill.text = helper.convertToFormatMoneyIDRFilter(bill.toString())
    }

}